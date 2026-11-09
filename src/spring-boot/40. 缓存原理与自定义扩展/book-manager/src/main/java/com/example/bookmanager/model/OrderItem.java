package com.example.bookmanager.model;

import lombok.Data;

@Data
public class OrderItem {
    private Long id;
    private Integer quantity;
    private Double price;
    private Long orderId;
    private Long bookId;
    private Book book;  // 关联的图书
}