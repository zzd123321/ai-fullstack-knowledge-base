package com.example.bookmanager.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.bookmanager.model.Book;

import java.util.List;

public interface BookService {
    Book createBook(Book book);
    Book getBookById(Long id);
    List<Book> getAllBooks();
    Book updateBook(Book book);
    boolean deleteBook(Long id);

    List<Book> searchBooks(String author, String category, Double minPrice, Double maxPrice);

    List<Book> complexSearch(String keyword, String category, Double minPrice, Double maxPrice);

    Book findOneByTitle(String title);

    long countByCategory(String category);

    int updatePriceByCategory(String category, Double multiplier);

    Page<Book> getBooksByPage(int pageNum, int pageSize, String author, String category);
}