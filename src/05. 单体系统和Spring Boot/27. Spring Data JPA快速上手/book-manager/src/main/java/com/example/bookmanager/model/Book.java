package com.example.bookmanager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "book")
@Data
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, length = 100)
    private String author;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer stock;

    private Integer sales; //允许为空，默认为0，可以在业务层处理

    @Column(length = 50)
    private String category;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    //可以添加业务构造器，便于创建实例
    public Book(String title, String author, Double price, Integer stock, Integer sales, String category) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.stock = stock;
        this.sales = sales;
        this.category = category;
    }
}