package com.example.bookmanager.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
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
            if(cache.getNativeCache() instanceof ConcurrentMap<?, ?> concurrentMap) {
                stats.put(cacheName, (long)concurrentMap.size());
            }
        }

        return stats;
    }

    // 查看指定缓存名称下的所有键值对
    public Map<Object, Object> inspectCache(String cacheName) {
        Map<Object, Object> result = new HashMap<>();

        // 1. 获取Cache对象
        Cache cache = cacheManager.getCache(cacheName);

        // 2. 获取原生缓存对象：该案例中一定是ConcurrentHashMap
        Object nativeCache = cache.getNativeCache();
        if(!(nativeCache instanceof ConcurrentMap)) {
            throw new IllegalArgumentException("底层缓存不是ConcurrentHashMap，实际类型为：" + nativeCache.getClass());
        }

        // 3. 强制转化类型并遍历
        ConcurrentMap<Object, Object> nativeMap = (ConcurrentMap<Object, Object>) nativeCache;
        result.putAll(nativeMap);

        return result;
    }

}
