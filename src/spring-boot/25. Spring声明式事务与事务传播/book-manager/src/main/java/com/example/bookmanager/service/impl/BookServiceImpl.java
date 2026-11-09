package com.example.bookmanager.service.impl;

import com.example.bookmanager.exception.BusinessException;
import com.example.bookmanager.exception.ResourceNotFoundException;
import com.example.bookmanager.model.Book;
import com.example.bookmanager.model.PageResult;
import com.example.bookmanager.repository.BookRepository;
import com.example.bookmanager.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.Set;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    @Lazy
    private BookService self;

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
    @Transactional(readOnly = true)
    public Book getBookById(Long id) {
        Book book = bookRepository.findById(id);
        if (book == null) {
            throw new ResourceNotFoundException("Book", id);
        }
        return book;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public Book updateBook(Book book) {
        if (book.getId() == null) {
            throw new IllegalArgumentException("更新图书时 ID 不能为空");
        }
        Book existing = bookRepository.findById(book.getId());
        if (existing == null) {
            throw new ResourceNotFoundException("要更新的图书不存在，ID: " + book.getId());
        }
        return bookRepository.update(book);
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public boolean deleteBook(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("删除时 ID 不能为空");
        }
        return bookRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getBooksByAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("作者名称不能为空");
        }
        return bookRepository.findByAuthor(author);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getBooksByPriceRange(Double min, Double max) {
        if (min == null || max == null || min < 0 || max < 0 || min > max) {
            throw new IllegalArgumentException("价格范围参数无效");
        }
        return bookRepository.findByPriceRange(min, max);
    }

    @Override
//    @Transactional(rollbackFor = Exception.class)
    public void importBooks(List<Book> books) {

        // 可以添加各种业务校验
        for(Book book : books) {
            if(book.getPrice() == null || book.getPrice() < 0) {
                throw new IllegalArgumentException("图书价格不能为负:" + book.getTitle());
            }
        }

        for(Book book : books) {
            self.createBook(book);
        }

//        bookRepository.batchInsert(books);
    }

    @Override
    public PageResult<Book> listBooks(int page, int size, String sortBy, String direction) {
        // 对sortBy和direction进行校验，防止SQL注入
        String validSortBy = validateSortBy(sortBy);
        String validDirection = validateDirection(direction);
        return bookRepository.findBooksWithPage(page, size, validSortBy, validDirection);
    }

    private String validateSortBy(String sortBy) {
        // 设置允许排序的字段，如果字段非法则默认使用id进行排序
        Set<String> allowed = Set.of("id", "title", "author", "price", "stock");
        if(sortBy == null || !allowed.contains(sortBy)) {
            return "id";
        }

        return sortBy;
    }

    private String validateDirection(String direction) {
        if("desc".equalsIgnoreCase(direction)) {
            return "DESC";
        }

        return "ASC";
    }
}