package com.example.bookmanager.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean(value = "caffeineCacheManager")
    @Primary
    public CacheManager caffeineCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        // 设置初始缓存名称
        cacheManager.setCacheNames(Arrays.asList("books", "orders"));

        //构造Caffeine配置
        cacheManager.setCaffeine(Caffeine.newBuilder()
                // 最大缓存条数
                .maximumSize(1000)
                // 写入后过期时间
                .expireAfterWrite(10, TimeUnit.MINUTES)
                // 访问后过期时间
                .expireAfterAccess(10, TimeUnit.MINUTES)
                // 开启统计
                .recordStats()
        );

        // 允许缓存null值
        cacheManager.setAllowNullValues(true);

        return cacheManager;
    }

    @Bean(value = "caffeineCacheManagerWithCustomCacheNames")
    public CacheManager caffeineCacheManagerWithCustomCacheNames() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager() {

            protected Cache<Object, Object > createNativeCaffeineCache(String name) {
                //根据缓存名称动态设置不同的过期时间
                Caffeine<Object, Object> builder;
                switch (name) {
                    case "hot":
                        builder = Caffeine.newBuilder()
                                .maximumSize(500)
                                .expireAfterWrite(2, TimeUnit.MINUTES);
                        break;
                    case "warm":
                        builder = Caffeine.newBuilder()
                                .maximumSize(1000)
                                .expireAfterWrite(10, TimeUnit.MINUTES);
                        break;
                    case "cold":
                    default:
                        builder = Caffeine.newBuilder()
                                .maximumSize(2000)
                                .expireAfterWrite(60, TimeUnit.MINUTES);

                }
                return builder.build();
            }
        };

        cacheManager.setCacheNames(Arrays.asList("hot", "warm", "cold"));

        // 允许缓存null值
        cacheManager.setAllowNullValues(true);

        return cacheManager;
    }
}
