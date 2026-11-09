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
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    public Order createOrder(List<OrderItemRequest> items) {
        // 1. 先创建订单（状态为 CREATED），获得 orderId
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        order.setStatus("CREATED");
        order.setTotalAmount(0.0);
        orderMapper.insert(order);

        double totalAmount = 0.0;

        // 2. 循环处理每个订单项：校验库存 → 扣减库存 → 插入订单项
        //    所有操作在同一事务中，任何失败都会整体回滚
        for (OrderItemRequest req : items) {
            // 2.1 查询图书信息（同时用于校验存在性和库存）
            Book book = bookMapper.selectById(req.getBookId());
            if (book == null) {
                throw new BusinessException("图书ID " + req.getBookId() + " 不存在");
            }
            if (book.getStock() < req.getQuantity()) {
                throw new BusinessException("图书《" + book.getTitle() + "》库存不足，当前库存：" + book.getStock());
            }

            // 2.2 扣减库存
            Book updateBook = new Book();
            updateBook.setId(book.getId());
            updateBook.setStock(book.getStock() - req.getQuantity());
            bookMapper.updateById(updateBook);

            // 2.3 创建订单项并插入（单价取下单时的图书价格，防止后续价格变动）
            OrderItem item = new OrderItem();
            item.setOrderId(order.getId());
            item.setBookId(req.getBookId());
            item.setQuantity(req.getQuantity());
            item.setPrice(book.getPrice());   // 快照价格
            orderItemMapper.insert(item);

            // 2.4 累加订单总金额
            totalAmount += book.getPrice() * req.getQuantity();
        }

        // 3. 更新订单总金额
        order.setTotalAmount(totalAmount);
        orderMapper.update(order);

        // 4. 返回完整的订单信息（包含所有订单项及其关联的图书详情）
        //    利用 OrderMapper 中定义的 selectOrderWithItems（JOIN 查询）一次性加载
        return orderMapper.selectOrderWithItems(order.getId());
    }


    @Override
    @Transactional(readOnly = true)
    public Order getOrderWithItems(Long orderId) {
        return orderMapper.selectOrderWithItems(orderId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getAllOrdersWithItems() {
        return orderMapper.selectAllWithItems();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> searchOrders(String status, LocalDateTime startDate, LocalDateTime endDate) {
        return orderMapper.searchOrders(status, startDate, endDate);
    }

    @Override
    public void cancelOrder(Long orderId) {
        Order order = orderMapper.selectById(orderId);
        if (order == null) throw new BusinessException("订单不存在");
        if ("PAID".equals(order.getStatus())) throw new BusinessException("已支付订单不能取消");
        order.setStatus("CANCELLED");
        orderMapper.update(order);
    }
}