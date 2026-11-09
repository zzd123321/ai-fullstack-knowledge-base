package com.example.bookmanager.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class MockCacheHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {

        try {
            // 模拟检查缓存服务
            boolean cacheAvailable = checkCache();
            if(cacheAvailable) {
                return Health.up()
                        .withDetail("service", "cache")
                        .withDetail("message", "缓存服务正常")
                        .build();
            } else {
                return Health.down()
                        .withDetail("service", "cache")
                        .withDetail("message", "缓存服务不可用")
                        .build();
            }
        } catch (Exception e) {
            return Health.down()
                    .withDetail("service", "cache")
                    .build();
        }
    }


    private boolean checkCache() {
        // 实际情况下需要检查缓存的执行状态
        return true;
    }
}
