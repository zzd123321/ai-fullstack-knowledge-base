package com.example.bookmanager.service;

import com.example.bookmanager.model.Order;

public interface OrderService {

    Order createOrder(Long bookId, int quantity);
}
