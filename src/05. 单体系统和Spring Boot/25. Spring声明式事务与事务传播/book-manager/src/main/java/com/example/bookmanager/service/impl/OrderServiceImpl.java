package com.example.bookmanager.service.impl;

import com.example.bookmanager.exception.BusinessException;
import com.example.bookmanager.model.Order;
import com.example.bookmanager.repository.BookRepository;
import com.example.bookmanager.repository.OrderRepository;
import com.example.bookmanager.service.AuditService;
import com.example.bookmanager.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class OrderServiceImpl implements OrderService {

    private final BookRepository bookRepository;
    private final OrderRepository orderRepository;
    private final AuditService auditService;

    public OrderServiceImpl(BookRepository bookRepository, OrderRepository orderRepository, AuditService auditService) {
        this.bookRepository = bookRepository;
        this.orderRepository = orderRepository;
        this.auditService = auditService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Order createOrder(Long bookId, int quantity) {

        // 添加审计日志
        // 即使主事务回滚，该日志也会被提交
        auditService.logOperation("创建订单：bookId=" + bookId + ", quantity=" + quantity);

        int currentStock = bookRepository.getStock(bookId);
        if (currentStock < quantity) {
            throw new BusinessException("库存不足，当前库存：" + currentStock);
        }

        // 创建订单并保存
        Order order = new Order();
        order.setBookId(bookId);
        order.setQuantity(quantity);
        order.setStatus("CREATED");
        Order savedOrder = orderRepository.save(order);

        // 减少图书的库存
        int rows = bookRepository.decreaseStock(bookId, quantity);
        if(rows != 1) {
            throw new BusinessException("扣减库存失败");
        }

        // 增加图书的销量
        bookRepository.increaseSales(bookId, quantity);

        return savedOrder;
    }
}
