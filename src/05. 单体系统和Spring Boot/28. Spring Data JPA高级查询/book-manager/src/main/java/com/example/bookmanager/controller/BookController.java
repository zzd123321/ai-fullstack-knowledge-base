package com.example.bookmanager.controller;

import com.example.bookmanager.model.Book;
import com.example.bookmanager.model.Result;
import com.example.bookmanager.model.ResultCode;
import com.example.bookmanager.repository.dto.BookSummary;
import com.example.bookmanager.service.BookService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // ---------- 基础 CRUD ----------
    @GetMapping
    public Result<List<Book>> getAllBooks() {
        return Result.success(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public Result<Book> getBook(@Min(1) @PathVariable Long id) {
        return Result.success(bookService.getBookById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Result<Book> createBook(@Valid @RequestBody Book book) {
        Book created = bookService.createBook(book);
        return Result.success(ResultCode.CREATED.getMessage(), created);
    }

    @PutMapping("/{id}")
    public Result<Book> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody Book book) {
        book.setId(id);
        return Result.success(bookService.updateBook(book));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Result<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return Result.success(ResultCode.NO_CONTENT.getMessage(), null);
    }

    // ---------- 查询：按作者 ----------
    @GetMapping("/author")
    public Result<List<Book>> getBooksByAuthor(@RequestParam String name) {
        return Result.success(bookService.getBooksByAuthor(name));
    }

    // ---------- 查询：按标题关键字搜索 ----------
    @GetMapping("/search")
    public Result<List<Book>> searchBooksByTitle(@RequestParam String keyword) {
        return Result.success(bookService.searchBooksByTitle(keyword));
    }

    // ---------- 查询：查询结果封装 ----------
    @GetMapping("/summaries")
    public Result<List<BookSummary>> getBookSummaries(@RequestParam Double minPrice) {
        return Result.success(bookService.findBookSummaries(minPrice));
    }

    // ---------- 查询：价格高于指定值（分页） ----------
    @GetMapping("/above-price")
    public Result<Page<Book>> getBooksAbovePrice(
            @RequestParam Double minPrice,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Book> result = bookService.getBooksAbovePrice(minPrice, page, size);
        return Result.success(result);
    }

    // ---------- 查询：按作者和价格区间 ----------
    @GetMapping("/search-by-author-price")
    public Result<List<Book>> searchByAuthorAndPrice(
            @RequestParam String author,
            @RequestParam(required = false) Double min,
            @RequestParam(required = false) Double max) {
        return Result.success(bookService.searchByAuthorAndPrice(author, min, max));
    }

    // ---------- 聚合查询 ----------
    @GetMapping("/stats/average-price")
    public Result<Double> getAveragePrice() {
        return Result.success(bookService.getAveragePrice());
    }

    @GetMapping("/stats/max-price")
    public Result<Double> getMaxPriceByCategory(@RequestParam String category) {
        return Result.success(bookService.getMaxPriceByCategory(category));
    }

    // ---------- 更新操作 ----------
    @PutMapping("/{id}/stock")
    public Result<Integer> updateStock(
            @PathVariable Long id,
            @RequestParam int delta) {
        int updated = bookService.updateStock(id, delta);
        return Result.success("库存更新成功，影响行数：" + updated, updated);
    }

    @DeleteMapping("/category/{category}")
    public Result<Integer> deleteByCategory(@PathVariable String category) {
        int deleted = bookService.deleteByCategory(category);
        return Result.success("按分类删除成功，影响行数：" + deleted, deleted);
    }

    // ---------- 排序与分页（多字段排序） ----------
    @GetMapping("/page")
    public Result<Page<Book>> getBooksWithSort(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        Page<Book> result = bookService.getBooksWithSort(page, size, sortBy, direction);
        return Result.success(result);
    }

    @GetMapping("/sorted-by-price-desc")
    public Result<List<Book>> getBooksSortedByPriceDesc() {
        return Result.success(bookService.getBooksSortedByPriceDesc());
    }

    @GetMapping("/sorted-multi")
    public Result<List<Book>> getBooksSortedByMultipleFields() {
        return Result.success(bookService.getBooksSortedByMultipleFields());
    }
}