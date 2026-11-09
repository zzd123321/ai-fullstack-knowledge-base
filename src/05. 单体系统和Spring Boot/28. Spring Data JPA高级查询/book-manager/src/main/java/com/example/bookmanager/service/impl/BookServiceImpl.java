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

    // ---------- 查询 ----------
    @Override
    public List<Book> getBooksByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    @Override
    public List<Book> searchBooksByTitle(String keyword) {
        return bookRepository.searchByTitleNative(keyword);
    }

    @Override
    public Page<Book> getBooksByCategoryWithPagination(String category, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return bookRepository.findByCategory(category, pageable);
    }

    @Override
    public Page<Book> getBooksAbovePrice(Double minPrice, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return bookRepository.findBooksAbovePrice(minPrice, pageable);
    }

    @Override
    public List<Book> searchByAuthorAndPrice(String author, Double min, Double max) {
        return bookRepository.searchByAuthorAndPrice(author, min, max);
    }

    // ---------- 聚合查询 ----------
    @Override
    public Double getAveragePrice() {
        return bookRepository.getAveragePrice();
    }

    @Override
    public Double getMaxPriceByCategory(String category) {
        return bookRepository.getMaxPriceByCategory(category);
    }

    // ---------- 更新操作 ----------

    @Override
    public int updateStock(Long id, int delta) {
        return bookRepository.updateStock(id, delta);
    }

    @Override
    public int deleteByCategory(String category) {
        return bookRepository.deleteByCategory(category);
    }

    @Override
    public List<BookSummary> findBookSummaries(Double minPrice) {
        return bookRepository.findBookSummaries(minPrice);
    }

    @Override
    public Page<Book> getBooksByCategory(String category, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size); // page 从 0 开始
        return bookRepository.findByCategory(category, pageable);
    }

    @Override
    public Page<Book> getBooksWithSort(int page, int size, String sortBy, String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        return bookRepository.findAll(pageable);
    }

    @Override
    public List<Book> getBooksSortedByPriceDesc() {
        Sort sort = Sort.by(Sort.Direction.DESC, "price");
        return bookRepository.findAll(sort);
    }

    @Override
    public List<Book> getBooksSortedByMultipleFields() {
        Sort sort = Sort.by(Sort.Order.desc("price"), Sort.Order.asc("title"));
        return bookRepository.findAll(sort);
    }
}