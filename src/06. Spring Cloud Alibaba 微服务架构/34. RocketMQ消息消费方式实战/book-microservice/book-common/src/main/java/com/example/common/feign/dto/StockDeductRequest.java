package com.example.common.feign.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class StockDeductRequest implements Serializable {

    private Long productId;
    private Integer quantity;
}