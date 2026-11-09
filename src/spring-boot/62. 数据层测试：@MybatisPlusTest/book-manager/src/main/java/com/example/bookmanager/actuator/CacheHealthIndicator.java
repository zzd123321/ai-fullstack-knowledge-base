package com.example.bookmanager.actuator;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
public class CacheHealthIndicator extends AbstractHealthIndicator {

    private final CacheManager cacheManager;

    public CacheHealthIndicator(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        if(cacheManager == null) {
            builder.down()
                    .withDetail("cacheManager", "未配置")
                    .build();
            return;
        }

        // 检查books缓存是否可用
        var cache = cacheManager.getCache("books");
        builder.up()
                .withDetail("cacheName", "books")
                .withDetail("cacheType", cache.getNativeCache().getClass().getSimpleName())
                .build();

    }
}
