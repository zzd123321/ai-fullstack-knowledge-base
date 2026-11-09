package com.example.bookmanager.service;

import com.example.bookmanager.model.Order;
import com.example.bookmanager.model.dto.OrderItemRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {

    Order createOrder(List<OrderItemRequest> items);
    Order getOrderWithItems(Long orderId);
    List<Order> getAllOrdersrWithItems();
    List<Order> searchOrders(String status, LocalDateTime startDate, LocalDateTime endDate);
    void cancelOrder(Long orderId);
}
