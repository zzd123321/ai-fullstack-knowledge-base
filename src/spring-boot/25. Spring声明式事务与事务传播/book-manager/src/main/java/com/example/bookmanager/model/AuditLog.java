package com.example.bookmanager.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AuditLog {
    private Long id;
    private String message;
    private LocalDateTime createdAt;
}