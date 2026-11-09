package com.example.bookmanager.exception;

public class ResourceNotFoundException extends BusinessException {
    public ResourceNotFoundException(String resource, Long id) {
        super(resource + "不存在，ID: " + id);
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}