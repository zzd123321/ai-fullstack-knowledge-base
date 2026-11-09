package com.example.bookmanager.repository;


import com.example.bookmanager.model.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;


@Repository
public class BookRepository {

    // TODO：使用数据库执行SQL操作
    // 使用内存存储来模拟数据库操作，后续课程会替换为真正的数据库
    private final Map<Long, Book> store = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    public Book save(Book book) {
        // 如果book不包含主键的ID，创建ID
        if(book.getId() == null) {
            Long id = idGenerator.incrementAndGet();
            book.setId(id);
        }
        // 如果book包含主键的ID，直接保存
        store.put(book.getId(), book);
        return book;
    }

    public Book findById(Long id) {
        return store.get(id);
    }

    public List<Book> findAll() {
        return new ArrayList<>(store.values());
    }

    public boolean deleteById(Long id) {
        return store.remove(id) != null;
    }

    public Book update(Book book) {
        if(store.containsKey(book.getId())) {
            store.put(book.getId(),book);
            return book;
        }

        return null;
    }

    // 根据Author查询
    public List<Book> findByAuthor(String author) {
        List<Book> books = new ArrayList<>();
        for(Book book : store.values()) {
            if(book.getAuthor().equals(author)) {
                books.add(book);
            }
        }

        return books;
    }

    // 按价格范围查询
    public List<Book> findByPriceRange(Double min, Double max) {
        List<Book> result = new ArrayList<>();
        for (Book book : store.values()) {
            if (book.getPrice() >= min && book.getPrice() <= max) {
                result.add(book);
            }
        }
        return result;
    }
}
