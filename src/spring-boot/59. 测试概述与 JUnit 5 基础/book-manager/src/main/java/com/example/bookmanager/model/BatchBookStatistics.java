package com.example.bookmanager.model;

import lombok.Data;

@Data
public class BatchBookStatistics {
    private String category;
    private Long bookCount;
    private Long totalStock;
    private Double avgPrice;
}
