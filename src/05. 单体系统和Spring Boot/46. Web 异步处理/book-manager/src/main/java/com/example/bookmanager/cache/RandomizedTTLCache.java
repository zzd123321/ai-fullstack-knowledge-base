package com.example.bookmanager.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

public class RandomizedTTLCache implements Cache {

    private final String name;
    private final Map<Object, CacheEntry> store = new ConcurrentHashMap<Object, CacheEntry>();
    private final long baseTtlMillis;
    private final long randomTtlMillis;

    public RandomizedTTLCache(String name, long baseTtlMillis, long randomTtlMillis) {
        this.name = name;
        this.baseTtlMillis = baseTtlMillis;
        this.randomTtlMillis = randomTtlMillis;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object getNativeCache() {
        return store;
    }

    @Override
    public ValueWrapper get(Object key) {
        CacheEntry entry = store.get(key);
        if(entry == null || entry.isExpired()) {
            store.remove(key);
            return null;
        }

        return new SimpleValueWrapper(entry.value);
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        return null;
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        return null;
    }

    @Override
    public void put(Object key, Object value) {
        // 设置随时的过期时间
        long ttl = baseTtlMillis + (long) (Math.random() * randomTtlMillis);
        store.put(key, new CacheEntry(value, System.currentTimeMillis() + ttl));
    }

    @Override
    public void evict(Object key) {
        store.remove(key);
    }

    @Override
    public void clear() {
        store.clear();
    }

    // 内部缓存条目数据结构
    private static class CacheEntry {
        final Object value;
        final long expiryTime;

        public CacheEntry(Object value, long expiryTime) {
            this.value = value;
            this.expiryTime = expiryTime;
        }

        boolean isExpired() {
            return System.currentTimeMillis() > expiryTime;
        }
    }
}
