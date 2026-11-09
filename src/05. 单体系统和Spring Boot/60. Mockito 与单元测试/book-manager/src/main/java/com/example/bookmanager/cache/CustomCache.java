package com.example.bookmanager.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class CustomCache implements Cache {

    private static final Logger logger = LoggerFactory.getLogger(CustomCache.class);

    private final String name;
    private final Map<Object, Object> store = new ConcurrentHashMap<Object, Object>();
    private final boolean allowNullValues;

    // 统计信息的维度
    private final AtomicLong hitCount = new AtomicLong(0);
    private final AtomicLong missCount = new AtomicLong(0);
    private final AtomicLong putCount = new AtomicLong(0);
    private final AtomicLong evictCount = new AtomicLong(0);

    public CustomCache(String name) {
        this(name, true);
    }

    public CustomCache(String name, boolean allowNullValues) {
        Assert.notNull(name, "Cache名称不能为空");
        this.name = name;
        this.allowNullValues = allowNullValues;
        logger.info("创建自定义缓存：{}", name);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Object getNativeCache() {
        return this.store;
    }

    @Override
    public ValueWrapper get(Object key) {
        Object value = this.store.get(key);
        if(value != null || store.containsKey(key)) {
            hitCount.incrementAndGet();
            logger.debug("缓存命中：{} -> {}", key, value);
            return new SimpleValueWrapper(value);
        }

        missCount.incrementAndGet();
        logger.debug("缓存未命中：{}", key);
        return null;
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
        if(!this.allowNullValues && value == null) {
            throw new IllegalArgumentException("不允许缓存null值");
        }

        store.put(key, value);
        putCount.incrementAndGet();
        logger.debug("缓存存储：{} -> {}", key, value);
    }

    @Override
    public void evict(Object key) {
        Object removed = this.store.remove(key);
        if(removed != null) {
            evictCount.incrementAndGet();
            logger.debug("缓存清除：{}", key);
        }
    }

    @Override
    public void clear() {
        store.clear();
        logger.debug("缓存清空：{}", name);
    }

    // 统计方法的暴露
    public long getHitCount() {return hitCount.get();}
    public long getMissCount() {return missCount.get();}
    public long getPutCount() {return putCount.get();}
    public long getEvictCount() {return evictCount.get();}
    public double getHitRate() {
        long total = hitCount.get() + missCount.get();
        return total == 0 ? 0 : (double)hitCount.get() / total;
    }
    public int getSize() {
        return store.size();
    }
}
