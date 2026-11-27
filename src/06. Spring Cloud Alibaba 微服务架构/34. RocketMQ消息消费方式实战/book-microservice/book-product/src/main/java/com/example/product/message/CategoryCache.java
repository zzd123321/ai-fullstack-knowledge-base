package com.example.product.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class CategoryCache {

    private Map<String, String> cache = new ConcurrentHashMap<>();

    public void refresh() {
        // 模拟数据库重新加载数据进行刷新
        Map<String, String> newCache = new ConcurrentHashMap<>();
        newCache.put("编程", "PGOGRAMMING");
        newCache.put("文学", "LITERATURE");

        this.cache = newCache;

        log.info("[缓存] 分类数据已重新加载，条目数={}", newCache.size());
    }
}
