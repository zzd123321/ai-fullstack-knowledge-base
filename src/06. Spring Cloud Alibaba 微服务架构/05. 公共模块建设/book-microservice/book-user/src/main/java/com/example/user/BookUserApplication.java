package com.example.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example")
@MapperScan("com.example.user.mapper")
public class BookUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookUserApplication.class, args);
    }
}