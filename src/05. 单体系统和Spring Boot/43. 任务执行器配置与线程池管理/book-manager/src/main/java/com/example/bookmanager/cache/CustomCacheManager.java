package com.example.bookmanager.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CustomCacheManager implements CacheManager {

    // 缓存存储容器
    private final Map<String, Cache> caches = new ConcurrentHashMap<>();

    // 是否允许动态创建缓存
    private boolean dynamic = true;

    // 是否允许缓存null值
    private boolean allowNullValues = true;

    // 默认缓存名称列表（如果非动态创建缓存，则只有这些缓存可以用）
    private Collection<String> cacheNames;

    public CustomCacheManager() {
        this(true);
    }

    public CustomCacheManager(boolean dynamic) {
        this.dynamic = dynamic;
    }

    @Override
    public Cache getCache(String name) {

        // 从缓存池中获取Cache对象
        Cache cache = caches.get(name);
        if(cache != null) {
            return cache;
        }

        // 如果允许动态创建缓存，则创建一个新缓存
        if(this.dynamic) {
            // 创建新缓存，使用自定义的CustomCache
            cache = createCache(name);
            caches.put(name, cache);
            return cache;
        }


        return null;
    }

    // 包装创建缓存的过程，可被子类复写
    protected Cache createCache(String name) {
        return new CustomCache(name, allowNullValues);
    }

    @Override
    public Collection<String> getCacheNames() {
        return caches.keySet();
    }

    public void setAllowNullValues(boolean allowNullValues) {
        this.allowNullValues = allowNullValues;
    }

    public void setCacheNames(Collection<String> cacheNames) {
        this.cacheNames = cacheNames;

        // 缓存预创建
        if(cacheNames != null) {
            for(String cacheName : cacheNames) {
                getCache(cacheName);
            }
        }
    }
}
