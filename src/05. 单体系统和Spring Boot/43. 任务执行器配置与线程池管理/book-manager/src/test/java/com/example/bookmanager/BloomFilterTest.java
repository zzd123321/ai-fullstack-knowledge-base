package com.example.bookmanager;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.nio.charset.StandardCharsets;

public class BloomFilterTest {
    public static void main(String[] args) {
        // 创建布隆过滤器：预计插入 100 万个元素，误判率 1%
        BloomFilter<String> bloomFilter = BloomFilter.create(
                Funnels.stringFunnel(StandardCharsets.UTF_8),
                1_000_000,
                0.01
        );

        // 1. 插入一些数据（模拟已存在的 ID）
        for (int i = 1; i <= 100_000; i++) {
            bloomFilter.put("user_" + i);
        }

        // 2. 测试存在的数据
        System.out.println("===== 存在的数据（应该返回 true） =====");
        System.out.println("user_1: " + bloomFilter.mightContain("user_1"));
        System.out.println("user_50000: " + bloomFilter.mightContain("user_50000"));
        System.out.println("user_100000: " + bloomFilter.mightContain("user_100000"));

        // 3. 测试不存在的数据
        System.out.println("\n===== 不存在的数据（大概率返回 false） =====");
        System.out.println("user_100001: " + bloomFilter.mightContain("user_100001"));
        System.out.println("user_999999: " + bloomFilter.mightContain("user_999999"));
        System.out.println("unknown_key: " + bloomFilter.mightContain("unknown_key"));

        // 4. 模拟误判（极少概率）
        System.out.println("\n===== 误判示例（极少数会误判） =====");
        int falsePositive = 0;
        int totalTest = 100_000;
        for (int i = 100_001; i <= 200_000; i++) {
            if (bloomFilter.mightContain("user_" + i)) {
                falsePositive++;
            }
        }
        System.out.println("测试 " + totalTest + " 个不存在元素，误判次数: " + falsePositive);
        System.out.println("误判率: " + (falsePositive * 100.0 / totalTest) + "%");
    }
}
