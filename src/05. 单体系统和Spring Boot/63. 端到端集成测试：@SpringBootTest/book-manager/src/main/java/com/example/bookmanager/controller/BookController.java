package com.example.bookmanager.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.bookmanager.model.Book;
import com.example.bookmanager.model.Result;
import com.example.bookmanager.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public Result<Book> create(@RequestBody Book book) {
        return Result.success(bookService.createBook(book));
    }

    @GetMapping("/{id}")
    public Result<Book> get(@PathVariable Long id) {
        return Result.success(bookService.getBookById(id));
    }

    @GetMapping
    public Result<List<Book>> list() {
        return Result.success(bookService.getAllBooks());
    }

    @PutMapping("/{id}")
    public Result<Book> update(@PathVariable Long id, @RequestBody Book book) {
        book.setId(id);
        return Result.success(bookService.updateBook(book));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        bookService.deleteBook(id);
        return Result.success();
    }

    @GetMapping("/search")
    public Result<List<Book>> search(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {
        return Result.success(bookService.searchBooks(author, category, minPrice, maxPrice));
    }

    @GetMapping("/complex")
    public Result<List<Book>> complexSearch(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {
        return Result.success(bookService.complexSearch(keyword, category, minPrice, maxPrice));
    }

    @GetMapping("/title")
    public Result<Book> findByTitle(@RequestParam String title) {
        return Result.success(bookService.findOneByTitle(title));
    }

    @GetMapping("/author")
    public Result<List<Book>> getBooksByAuthor(@RequestParam String author) {
        return Result.success(bookService.getBooksByAuthor(author));
    }

    @GetMapping("/count")
    public Result<Long> countByCategory(@RequestParam String category) {
        return Result.success(bookService.countByCategory(category));
    }

    @PutMapping("/price/batch")
    public Result<Integer> updatePriceByCategory(
            @RequestParam String category,
            @RequestParam Double multiplier) {
        int affected = bookService.updatePriceByCategory(category, multiplier);
        return Result.success(affected);
    }

    @GetMapping("/page")
    public Result<Page<Book>> page(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String category) {
        Page<Book> page = bookService.getBooksByPage(pageNum, pageSize, author, category);
        return Result.success(page);
    }
}