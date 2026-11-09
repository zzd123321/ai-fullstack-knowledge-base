package com.example.bookmanager.service;

import com.example.bookmanager.model.Book;

import java.util.List;

public interface BookService {
    Book createBook(Book book);
    Book getBookById(Long id);
    List<Book> getAllBooks();
    Book updateBook(Book book);
    boolean deleteBook(Long id);
}