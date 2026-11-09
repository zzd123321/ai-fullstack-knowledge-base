package com.example.bookmanager.service.impl;

import com.example.bookmanager.annotation.Cached;
import com.example.bookmanager.model.Book;
import com.example.bookmanager.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Cached
public class CachedBookServiceImpl implements BookService {
    @Override
    public Book createBook(Book book) {
        return null;
    }

    @Override
    public Book getBookById(Long id) {
        return null;
    }

    @Override
    public List<Book> getAllBooks() {
        return List.of();
    }

    @Override
    public Book updateBook(Book book) {
        return null;
    }

    @Override
    public boolean deleteBook(Long id) {
        return false;
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        return List.of();
    }

    @Override
    public List<Book> getBooksByPriceRange(Double min, Double max) {
        return List.of();
    }
}
