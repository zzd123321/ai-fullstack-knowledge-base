package com.example.order.service.vo;

import lombok.Data;

@Data
public class OrderVO {
    private Long id;
    private String orderNumber;
    private Long userId;
    private Double totalAmount;
    private String status;
}
