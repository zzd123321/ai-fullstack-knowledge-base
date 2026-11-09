package com.example.bookmanager.service.impl;

import com.example.bookmanager.exception.BusinessException;
import com.example.bookmanager.model.Order;
import com.example.bookmanager.repository.BookRepository;
import com.example.bookmanager.repository.OrderRepository;
import com.example.bookmanager.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class OrderServiceImpl implements OrderService {

    private final BookRepository bookRepository;
    private final OrderRepository orderRepository;
    private final TransactionTemplate transactionTemplate;

    public OrderServiceImpl(BookRepository bookRepository, OrderRepository orderRepository, TransactionTemplate transactionTemplate) {
        this.bookRepository = bookRepository;
        this.orderRepository = orderRepository;
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public Order createOrder(Long bookId, int quantity) {
        return transactionTemplate.execute(status -> {
            // 判断库存是否足够
            int currentStock = bookRepository.getStock(bookId);
            if (currentStock < quantity) {
                status.setRollbackOnly();
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
                status.setRollbackOnly();
                throw new BusinessException("扣减库存失败");
            }

            // 增加图书的销量
            bookRepository.increaseSales(bookId, quantity);

            return savedOrder;
        });
    }
}
