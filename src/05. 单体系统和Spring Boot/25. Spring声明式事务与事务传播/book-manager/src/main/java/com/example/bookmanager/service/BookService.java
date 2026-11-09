package com.example.bookmanager.service;

import com.example.bookmanager.model.Book;
import com.example.bookmanager.model.PageResult;

import java.util.List;

public interface BookService {
    Book createBook(Book book);
    Book getBookById(Long id);
    List<Book> getAllBooks();
    Book updateBook(Book book);
    boolean deleteBook(Long id);
    List<Book> getBooksByAuthor(String author);
    List<Book> getBooksByPriceRange(Double min, Double max);

    // 批量导入图书+分页查询图书
    void importBooks(List<Book> books);
    PageResult<Book> listBooks(int page, int size, String sortBy, String direction);
}