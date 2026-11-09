package com.example.rbac.dto;

import lombok.Data;

@Data
public class ArticleRequest {
    private String title;
    private String content;
    private Long authorId;
}
