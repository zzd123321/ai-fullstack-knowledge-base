package com.example.webfluxdemo.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {

    // 唯一标识符
    private Long id;

    // 用户名（登录名）
    private String username;

    // 电子邮箱
    private String email;

    // 密码（实际开发中应存储加密后的密文）
    private String password;

    // 创建时间
    private LocalDateTime createdAt;

    // 更新时间
    private LocalDateTime updatedAt;
}
