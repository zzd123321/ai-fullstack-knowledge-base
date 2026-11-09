package com.example.bookmanager.service;

import com.example.bookmanager.model.Order;
import com.example.bookmanager.model.dto.OrderItemRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderService {

    @Transactional
    Order createOrder(List<OrderItemRequest> orderItems);

    @Transactional(readOnly = true)
    Order getOrderById(Long orderId);

    @Transactional(readOnly = true)
    List<Order> getAllOrders();

    @Transactional(readOnly = true)
    List<Order> getOrdersByStatus(String status);

    @Transactional
    void cancelOrder(Long orderId);
}
