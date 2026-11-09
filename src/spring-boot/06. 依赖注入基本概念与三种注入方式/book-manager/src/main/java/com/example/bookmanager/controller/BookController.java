package com.example.bookmanager.controller;

import com.example.bookmanager.model.Book;
import com.example.bookmanager.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

//    @Autowired
//    private BookService bookService;

    private final BookService bookService;

    // 构造器注入
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return bookService.createBook(book);
    }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book book) {
        book.setId(id);
        return bookService.updateBook(book);
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable Long id) {
        boolean deleted = bookService.deleteBook(id);
        return deleted ? "删除成功" : "删除失败";
    }

    @GetMapping("/author")
    public List<Book> getBooksByAuthor(@RequestParam String name) {
        return bookService.getBooksByAuthor(name);
    }

    @GetMapping("/price")
    public List<Book> getBooksByPriceRange(
            @RequestParam Double min,
            @RequestParam Double max) {
        return bookService.getBooksByPriceRange(min, max);
    }
}