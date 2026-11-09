package com.example.bookmanager.service;

import com.example.bookmanager.model.Book;
import com.example.bookmanager.repository.dto.BookSummary;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookService {
    Book createBook(Book book);
    Book getBookById(Long id);
    List<Book> getAllBooks();
    Book updateBook(Book book);
    boolean deleteBook(Long id);

    // ---------- 查询 ----------
    List<Book> getBooksByAuthor(String author);

    List<Book> searchBooksByTitle(String keyword);

    Page<Book> getBooksByCategoryWithPagination(String category, int page, int size);

    Page<Book> getBooksAbovePrice(Double minPrice, int page, int size);

    List<Book> searchByAuthorAndPrice(String author, Double min, Double max);

    // ---------- 聚合查询 ----------
    Double getAveragePrice();

    Double getMaxPriceByCategory(String category);

    int updateStock(Long id, int delta);

    int deleteByCategory(String category);

    List<BookSummary> findBookSummaries(Double minPrice);

    Page<Book> getBooksByCategory(String category, int page, int size);
    Page<Book> getBooksWithSort(int page, int size, String sortBy, String direction);

    List<Book> getBooksSortedByPriceDesc();
    List<Book> getBooksSortedByMultipleFields();

}