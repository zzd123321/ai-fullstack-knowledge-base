package com.example.bookmanager.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean(value = "caffeineCacheManager")
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

//    @Bean
//    public CacheManager compositeCacheManager(
//            CacheManager caffeineCacheManager,
//            CacheManager redisCacheManager) {
//        CompositeCacheManager compositeCacheManager = new CompositeCacheManager();
//        // 指定多个缓存，确定执行的优先级
//        compositeCacheManager.setCacheManagers(
//                // 先查询本地缓存，如果没有查询到再访问分布式缓存
//                Arrays.asList(caffeineCacheManager, redisCacheManager)
//        );
//
//        // 通过回退（Fallback）实现当所有缓存都没有的时候允许空操作
//        compositeCacheManager.setFallbackToNoOpCache(true);
//        return compositeCacheManager;
//    }
}
