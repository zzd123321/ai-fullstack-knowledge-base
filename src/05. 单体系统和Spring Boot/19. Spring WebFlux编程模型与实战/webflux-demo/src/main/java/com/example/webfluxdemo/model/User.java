package com.example.webfluxdemo.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {

    private Long id;
    private String userName;
    private String password;
    private String email;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
