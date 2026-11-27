package com.example.product.service.vo;

import lombok.Data;

@Data
public class ProductVO {
    private Long id;
    private String title;
    private String author;
    private Double price;
    private Integer stock;
    private Integer sales;
    private String category;
    private String isbn;
    private String description;
}