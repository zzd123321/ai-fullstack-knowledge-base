package com.example.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@MapperScan("com.example.user.mapper")
public class ProductUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductUserApplication.class, args);
    }
}