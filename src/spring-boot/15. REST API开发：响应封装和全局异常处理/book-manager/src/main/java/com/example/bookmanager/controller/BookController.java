package com.example.bookmanager.controller;

import com.example.bookmanager.model.Book;
import com.example.bookmanager.model.Result;
import com.example.bookmanager.model.ResultCode;
import com.example.bookmanager.service.BookService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @PostMapping
    public Result<Book> createBook(@Valid @RequestBody Book book) { //自动把请求的JSON数据映射为一个Book对象
        return Result.success(ResultCode.CREATED.getMessage(), bookService.createBook(book));
    }

    @GetMapping
    public Result<List<Book>> getAllBooks() {
        return Result.success(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public Result<Book> getBook(@Min(1) @PathVariable Long id) {
        return Result.success(bookService.getBookById(id));
    }

    @PutMapping("/{id}")
    public Result<Book> updateBook(@PathVariable Long id, @Valid @RequestBody Book book) {
        book.setId(id);
        return Result.success(bookService.updateBook(book));
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return Result.success(ResultCode.NO_CONTENT.getMessage(), null);
    }

    @GetMapping("/author") //完整请求地址：api/books/author?name=tianmin
    public Result<List<Book>> getBooksByAuther(@RequestParam String name) {
        return Result.success(bookService.getBooksByAuthor(name));
    }

    @GetMapping("/price") //完整请求地址：api/books/price?min=1&max=1000
    public Result<List<Book>> getBooksByPriceRange(
            @DecimalMin("0.00") @RequestParam Double min,
            @DecimalMax("9999.99") @RequestParam Double max) {
        return Result.success(bookService.getBooksByPriceRange(min, max));
    }

    @GetMapping("/books")
    public Result<List<Book>> getBooks(@RequestHeader("User-Agent") String userAgent) {
        System.out.println("User-Agent: " + userAgent);
        return Result.success(bookService.getAllBooks());
    }

}
