package com.example.bookmanager.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Book implements Serializable {

    private Long id;
    private String title;
    private String author;
    private Double price;
    private Integer stock;
    private Integer sales;
    private String category;
    private String isbn;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}