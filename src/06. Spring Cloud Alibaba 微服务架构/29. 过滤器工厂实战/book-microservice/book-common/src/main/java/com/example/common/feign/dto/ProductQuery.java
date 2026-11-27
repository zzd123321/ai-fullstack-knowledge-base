package com.example.common.feign.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductQuery implements Serializable {

    private String keyword;
    private String category;
    private Double minPrice;
    private Double maxPrice;
}