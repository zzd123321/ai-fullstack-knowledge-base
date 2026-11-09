package com.example.bookmanager.service.impl;

import com.example.bookmanager.exception.BusinessException;
import com.example.bookmanager.mapper.BookMapper;
import com.example.bookmanager.mapper.OrderItemMapper;
import com.example.bookmanager.mapper.OrderMapper;
import com.example.bookmanager.model.Book;
import com.example.bookmanager.model.Order;
import com.example.bookmanager.model.OrderItem;
import com.example.bookmanager.model.dto.OrderItemRequest;
import com.example.bookmanager.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private BookMapper bookMapper;

    @Override
    @Transactional
    public Order createOrder(List<OrderItemRequest> items) {
        // 1. 先创建订单，获取OrderId
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        order.setStatus("CREATED");
        order.setTotalAmount(0.0);
        orderMapper.insert(order);

        double totalAmount = 0.0;

        // 2. 循环遍历每一个订单项：校验库存 -> 扣减库存 -> 插入订单项
        for (OrderItemRequest item : items) {
            // 2.1 查询图书信息，用于校验库存
            Book book = bookMapper.selectById(item.getBookId());
            if(book == null) {
                throw new BusinessException("图书Id " + item.getBookId() + "不存在");
            }

            if(book.getStock() < item.getQuantity()) {
                throw new BusinessException("图书: " + book.getTitle() + "库存不足，当前库存" + book.getStock());
            }

            // 2.1 扣减库存
            Book updateBook = new Book();
            updateBook.setId(book.getId());
            updateBook.setStock(book.getStock() - item.getQuantity());
            bookMapper.update(updateBook);

            // 2.3 创建订单项并插入
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getId());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setBookId(updateBook.getId());
            orderItem.setPrice(book.getPrice()); // 快照价格
            orderItemMapper.insert(orderItem);

            // 2.4 累加订单总金额
            totalAmount += book.getPrice() * item.getQuantity();
        }

        // 3. 更新订单总金额
        order.setTotalAmount(totalAmount);
        orderMapper.update(order);

        // 4. 返回完整的订单信息
        // 利用OrderMapper中定义的JOIN方式来一次性获取订单信息
        return orderMapper.selectOrderWithItems(order.getId());
    }

    @Override
    public Order getOrderWithItems(Long orderId) {
        return orderMapper.selectOrderWithItems(orderId);
    }

    @Override
    public List<Order> getAllOrdersrWithItems() {
        return orderMapper.selectAllWithItems();
    }

    @Override
    public List<Order> searchOrders(String status, LocalDateTime startDate, LocalDateTime endDate) {
        return orderMapper.searchOrder(status, startDate, endDate);
    }

    @Override
    public void cancelOrder(Long orderId) {
        Order order = orderMapper.selectById(orderId);
        if(order == null) {
            throw new BusinessException("订单不存在");
        }

        if("PAID".equals(order.getStatus())) {
            throw new BusinessException("已支付订单不能取消");
        }

        order.setStatus("CANCEL");
        orderMapper.update(order);
    }
}
