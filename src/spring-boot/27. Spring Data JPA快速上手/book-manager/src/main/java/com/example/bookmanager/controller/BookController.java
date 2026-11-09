package com.example.bookmanager.controller;

import com.example.bookmanager.model.Book;
import com.example.bookmanager.model.Result;
import com.example.bookmanager.model.ResultCode;
import com.example.bookmanager.service.BookService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
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
}