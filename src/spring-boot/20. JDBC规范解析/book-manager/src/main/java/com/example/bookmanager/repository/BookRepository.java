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
    // 使用内存存储模拟数据库（后续课程会替换为真实数据库）
    private final Map<Long, Book> store = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Book save(Book book) {
        if (book.getId() == null) {
            book.setId(idGenerator.getAndIncrement());
        }
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
        if (store.containsKey(book.getId())) {
            store.put(book.getId(), book);
            return book;
        }
        return null;
    }

    // 按作者查询
    public List<Book> findByAuthor(String author) {
        List<Book> result = new ArrayList<>();
        for (Book book : store.values()) {
            if (author.equals(book.getAuthor())) {
                result.add(book);
            }
        }
        return result;
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