package com.example.bookmanager.metric;

import com.example.bookmanager.model.Book;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
public class CacheMetrics {

    public CacheMetrics(CacheManager cacheManager, MeterRegistry meterRegistry) {

        // 1. 先拿到Spring中的Cache对象
        Cache springCache = cacheManager.getCache("books");

        // 2. 拿到底层原生的缓存：Caffeine
        com.github.benmanes.caffeine.cache.Cache<String, Book> nativeCache =
                (com.github.benmanes.caffeine.cache.Cache<String, Book>)springCache.getNativeCache();

        // 3. 用原生缓存注册Gauge
        Gauge.builder("books.cache.size", nativeCache, cache -> cache.asMap().size())
                .description("图书缓存条目数")
                .register(meterRegistry);
    }
}
