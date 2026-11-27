package com.example.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class BookOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookOrderApplication.class, args);
    }
}