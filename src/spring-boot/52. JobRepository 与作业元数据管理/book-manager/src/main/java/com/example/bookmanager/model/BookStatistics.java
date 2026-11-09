package com.example.bookmanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class BookStatistics {

    private Long totalBooks; // 图书总数
    private long totalStock; // 总库存
    private Double averagePrice; // 平均价格
    private Map<String, Long> categoryCount; // 各分类图书数量
    private String generatedAt; // 生成时间
}
