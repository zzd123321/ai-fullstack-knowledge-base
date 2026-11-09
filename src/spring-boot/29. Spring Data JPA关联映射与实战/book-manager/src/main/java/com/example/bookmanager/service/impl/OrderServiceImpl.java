package com.example.bookmanager.service.impl;

import com.example.bookmanager.exception.BusinessException;
import com.example.bookmanager.model.Book;
import com.example.bookmanager.model.Order;
import com.example.bookmanager.model.OrderItem;
import com.example.bookmanager.model.dto.OrderItemRequest;
import com.example.bookmanager.repository.BookRepository;
import com.example.bookmanager.repository.OrderRepository;
import com.example.bookmanager.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;

    public OrderServiceImpl(OrderRepository orderRepository, BookRepository bookRepository) {
        this.orderRepository = orderRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public Order createOrder(List<OrderItemRequest> orderItems) {
        // 1. 创建订单
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        order.setStatus("CREATED");

        double totalAmount = 0.0;

        // 2. 遍历每个订单项请求
        for (OrderItemRequest orderItem : orderItems) {
            Long bookId = orderItem.getBookId();
            Integer quantity = orderItem.getQuantity();

            // 3. 查询图书
            Book book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new BusinessException("图书不存在：" + bookId));

            // 4. 校验库存
            if(book.getStock() < quantity) {
                throw new BusinessException("库存不足，图书" + book.getTitle() +
                        "，剩余库存：" + book.getStock() + "，需要:" + quantity);
            }

            // 5. 扣减库存(这里先扣减，后面在事务中提交时写入数据库)
            book.setStock(book.getStock() - quantity);

            // 6. 创建订单项
            OrderItem item = new OrderItem();
            item.setBook(book);
            item.setQuantity(quantity);
            item.setPrice(book.getPrice());
            item.setOrder(order);

            // √ 7. 维护双向关联， order的addItem会同时设置item.setOrder(this)
            order.addItem(item);

            // 8. 累加总金额
            totalAmount += book.getPrice() * quantity;
        }

        // 9. 设置订单总金额
        order.setTotalAmount(totalAmount);

        // 10. 保存订单: CascadeType.ALL会级联报错所有的OrderItem
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findByIdWithGraph(orderId)
                .orElseThrow(() -> new RuntimeException("订单不存在"));
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAllWithGraph();
    }

    @Override
    public List<Order> getOrdersByStatus(String status) {
        return orderRepository.findByStatus(status);
    }

    @Override
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("订单不存在"));

        if("PAID".equals(order.getStatus())) {
            throw new RuntimeException("已支付订单不能取消");
        }

        order.setStatus("CANCELLED");
        orderRepository.save(order);
    }
}
