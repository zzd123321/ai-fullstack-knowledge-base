package com.example.bookmanager.cache;

import com.github.benmanes.caffeine.cache.stats.CacheStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

@Service
public class CacheInspectorService {

    @Autowired
    private CacheManager cacheManager;

    // 查看所有缓存的名称列表：包含名称和Size
    public Map<String, Long> getAllCacheNamesAndSizes() {
        Map<String, Long> stats = new HashMap<>();
        for (String cacheName : cacheManager.getCacheNames()) {

            Cache cache = cacheManager.getCache(cacheName);
            if(cache instanceof CaffeineCache caffeineCache) {
                // 只统计 CaffeineCache类型的缓存
                com.github.benmanes.caffeine.cache.Cache<Object, Object> nativeCache
                        = caffeineCache.getNativeCache();
                stats.put(cacheName, nativeCache.estimatedSize());
            }
        }

        return stats;
    }

    // 查看指定缓存名称下的所有键值对
    public Map<Object, Object> inspectCache(String cacheName) {
        // 1. 获取Cache对象
        Cache cache = cacheManager.getCache(cacheName);

        // 2. 获取Caffeine原生缓存对象
        // 注意：SpringCache的实际类型是CaffeineCache
        if(!(cache instanceof CaffeineCache caffeineCache)) {
            throw new IllegalArgumentException("底层缓存不是CaffeineCache， 实际类型是：" + cache.getClass());
        }
        com.github.benmanes.caffeine.cache.Cache<Object, Object> nativeCache
                = caffeineCache.getNativeCache();

        // 3. 获取nativeCache对象进行遍历
        ConcurrentMap<Object, Object> mapView = nativeCache.asMap();

        return new HashMap<>(mapView);
    }

    public Map<String, Object> getCacheStats(String cacheName) {
        Map<String, Object> statsMap = new HashMap<>();
        Cache springCache = cacheManager.getCache(cacheName);

        CaffeineCache caffeineCache = (CaffeineCache) springCache;
        com.github.benmanes.caffeine.cache.Cache<Object, Object> nativeCache
                = caffeineCache.getNativeCache();

        // 获取统计对象：需要开启recordStats标志，如果没有开启则所有值均为0
        CacheStats stats = nativeCache.stats();
        statsMap.put("hitCount", stats.hitCount());
        statsMap.put("missCount", stats.missCount());
        statsMap.put("hitRate", stats.hitRate());
        statsMap.put("evictionCount", stats.evictionCount());
        statsMap.put("loadSuccessCount", stats.loadSuccessCount());
        statsMap.put("averageLoadPenalty", stats.averageLoadPenalty());
        statsMap.put("currentSize", nativeCache.estimatedSize()); // 近似大小

        return statsMap;
    }

}
