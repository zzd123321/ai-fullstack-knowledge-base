package com.example.bookmanager.cache.jcache;

import javax.cache.annotation.GeneratedCacheKey;

class SimpleGeneratedCacheKey implements GeneratedCacheKey {
    private final Object key;

    public SimpleGeneratedCacheKey(Object key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        SimpleGeneratedCacheKey that = (SimpleGeneratedCacheKey) obj;
        return key.equals(that.key);
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }
}
