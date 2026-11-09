package com.example.bookmanager.repository.dto;

import lombok.Data;

@Data
public class BookSummary {
    private Long id;
    private String title;
    private String author;
    private Double price;

    // 构造方法（必须与 @Query 结果匹配）
    public BookSummary(Long id, String title, String author, Double price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
    }
}