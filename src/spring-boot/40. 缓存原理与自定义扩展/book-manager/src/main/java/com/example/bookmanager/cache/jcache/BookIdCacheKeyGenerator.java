package com.example.bookmanager.cache.jcache;

import com.example.bookmanager.model.Book;

import javax.cache.annotation.CacheKeyGenerator;
import javax.cache.annotation.CacheKeyInvocationContext;
import javax.cache.annotation.GeneratedCacheKey;
import java.lang.annotation.Annotation;

public class BookIdCacheKeyGenerator implements CacheKeyGenerator {
    @Override
    public GeneratedCacheKey generateCacheKey(CacheKeyInvocationContext<? extends Annotation> context) {
        Book book = (Book) context.getKeyParameters()[0];
        Long id = book.getId();
        if (id == null) {
            throw new IllegalStateException("Book id not generated yet");
        }
        // 返回一个简单的 GeneratedCacheKey，直接封装 id
        return new SimpleGeneratedCacheKey(id);
    }
}

// 简单的 GeneratedCacheKey 实现
