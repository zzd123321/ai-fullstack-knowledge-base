package com.example.bookmanager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppCacheConfig {

    @Value("${app.cache.enabled:true}")
    private boolean enabled;

    public boolean isEnabled() {
        return enabled;
    }
}
