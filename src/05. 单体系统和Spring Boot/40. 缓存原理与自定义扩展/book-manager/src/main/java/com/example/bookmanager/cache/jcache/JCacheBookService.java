package com.example.bookmanager.cache.jcache;

import com.example.bookmanager.mapper.BookMapper;
import com.example.bookmanager.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.cache.annotation.*;

@Service
public class JCacheBookService {

    @Autowired
    private BookMapper bookMapper;

    @CacheResult(cacheName = "jcache-books")
    public Book getBookById(@CacheKey Long id) {
        return bookMapper.selectById(id);
    }

    @CachePut(cacheName = "jcache-books", cacheKeyGenerator = BookIdCacheKeyGenerator.class)
    public Book saveBook(@CacheValue Book book) {
        bookMapper.insert(book);
        return book;
    }

    @CacheRemove(cacheName = "jcache-books")
    public void deleteBook(@CacheKey Long id) {
        bookMapper.deleteById(id);
    }
}