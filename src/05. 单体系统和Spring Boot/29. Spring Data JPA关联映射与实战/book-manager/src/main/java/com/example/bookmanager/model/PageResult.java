package com.example.bookmanager.model;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {
    private int page;
    private int size;
    private long total;
    private int totalPages;
    private List<T> data;

    public PageResult(int page, int size, long total, List<T> data) {
        this.page = page;
        this.size = size;
        this.total = total;
        this.totalPages = (int) Math.ceil((double) total / size);
        this.data = data;
    }
}