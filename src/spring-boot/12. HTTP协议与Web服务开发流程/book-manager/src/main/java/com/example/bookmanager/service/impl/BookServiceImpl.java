package com.example.bookmanager.service.impl;

import com.example.bookmanager.model.Book;
import com.example.bookmanager.repository.BookRepository;
import com.example.bookmanager.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl() {
        this.bookRepository = new BookRepository();
    }

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book createBook(Book book) {
        // 业务校验
        if(book.getPrice() == null || book.getPrice() < 0) {
            throw new IllegalArgumentException("图书价格不能为空或者为负数");
        }

        if(book.getStock() == null || book.getStock() < 0) {
            throw new IllegalArgumentException("图书库存不能为空或者为负数");
        }

        return bookRepository.save(book);
    }

    @Override
    public Book getBookById(Long id) {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Book book = bookRepository.findById(id);
        if (book == null) {
            throw new RuntimeException("图书不存在，ID: " + id);
        }
        return book;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book updateBook(Book book) {
        if (book.getId() == null) {
            throw new IllegalArgumentException("更新图书时 ID 不能为空");
        }
        Book existing = bookRepository.findById(book.getId());
        if (existing == null) {
            throw new RuntimeException("要更新的图书不存在，ID: " + book.getId());
        }
        return bookRepository.update(book);
    }

    @Override
    public boolean deleteBook(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("删除时 ID 不能为空");
        }
        return bookRepository.deleteById(id);
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("作者名称不能为空");
        }
        return bookRepository.findByAuthor(author);
    }

    @Override
    public List<Book> getBooksByPriceRange(Double min, Double max) {
        if (min == null || max == null || min < 0 || max < 0 || min > max) {
            throw new IllegalArgumentException("价格范围参数无效");
        }
        return bookRepository.findByPriceRange(min, max);
    }
}
