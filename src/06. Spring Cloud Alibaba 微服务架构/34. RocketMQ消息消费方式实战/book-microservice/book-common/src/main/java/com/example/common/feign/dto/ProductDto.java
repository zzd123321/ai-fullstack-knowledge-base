package com.example.common.feign.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductDto implements Serializable {

    private Long id;
    private String title;
    private String author;
    private Double price;
    private Integer stock;
    private String category;
    private String isbn;
    private String description;
}