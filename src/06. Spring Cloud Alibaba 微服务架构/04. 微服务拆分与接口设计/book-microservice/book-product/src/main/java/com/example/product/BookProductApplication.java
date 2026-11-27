package com.example.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.product.mapper")
public class BookProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookProductApplication.class, args);
    }
}