package com.example.bookmanager.service.impl;

import com.example.bookmanager.exception.BusinessException;
import com.example.bookmanager.exception.ResourceNotFoundException;
import com.example.bookmanager.model.Book;
import com.example.bookmanager.repository.BookRepository;
import com.example.bookmanager.repository.dto.BookSummary;
import com.example.bookmanager.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public Book createBook(Book book) {
        // 业务校验：价格不能为负数
        if (book.getPrice() == null || book.getPrice() < 0) {
            throw new BusinessException("图书价格不能为空或为负数");
        }
        // 业务校验：库存不能为负数
        if (book.getStock() == null || book.getStock() < 0) {
            throw new BusinessException("库存不能为负数");
        }

        System.out.println("添加Book：" + book.getTitle());
        return bookRepository.save(book);
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public Book updateBook(Book book) {
        if (book.getId() == null) {
            throw new IllegalArgumentException("更新图书时 ID 不能为空");
        }
        // 使用 Optional 正确判断记录是否存在
        Book existing = bookRepository.findById(book.getId())
                .orElseThrow(() -> new ResourceNotFoundException("要更新的图书不存在，ID: " + book.getId()));

        return bookRepository.save(book);
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public boolean deleteBook(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("删除时 ID 不能为空");
        }
        bookRepository.deleteById(id);
        return true;
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", id));
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}