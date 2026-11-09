package com.example.bookmanager.service.impl;

import com.example.bookmanager.exception.BusinessException;
import com.example.bookmanager.exception.ResourceNotFoundException;
import com.example.bookmanager.model.Book;
import com.example.bookmanager.mapper.BookMapper;
import com.example.bookmanager.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final BookMapper bookMapper;

    public BookServiceImpl(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Book getBookById(Long id) {
        Book book = bookMapper.selectById(id);
        if (book == null) {
            throw new ResourceNotFoundException("Book", id);
        }
        return book;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        return bookMapper.selectAll();
    }

    @Override
    public Book createBook(Book book) {
        if (book.getPrice() == null || book.getPrice() < 0) {
            throw new BusinessException("价格不能为负数");
        }
        if (book.getStock() == null || book.getStock() < 0) {
            throw new BusinessException("库存不能为负数");
        }
        // 销量默认为 0
        book.setSales(0);
        int rows = bookMapper.insert(book);
        if (rows != 1) {
            throw new BusinessException("新增图书失败");
        }
        return book;
    }

    @Override
    public Book updateBook(Book book) {
        if (book.getId() == null) {
            throw new IllegalArgumentException("更新时 ID 不能为空");
        }
        Book existing = bookMapper.selectById(book.getId());
        if (existing == null) {
            throw new ResourceNotFoundException("要更新的图书不存在，ID: " + book.getId());
        }
        int rows = bookMapper.update(book);
        if (rows != 1) {
            throw new BusinessException("更新图书失败");
        }
        return book;
    }

    @Override
    public boolean deleteBook(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("删除时 ID 不能为空");
        }
        Book existing = bookMapper.selectById(id);
        if (existing == null) {
            throw new ResourceNotFoundException("要删除的图书不存在，ID: " + id);
        }
        int rows = bookMapper.deleteById(id);
        return rows == 1;
    }
}