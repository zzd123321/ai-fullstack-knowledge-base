package com.example.bookmanager.controller;

import com.example.bookmanager.model.Book;
import com.example.bookmanager.service.BookService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
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


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(@Valid @RequestBody Book book) { //自动把请求的JSON数据映射为一个Book对象
        return bookService.createBook(book);
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public Book getBook(@Min(1) @PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @Valid @RequestBody Book book) {
        book.setId(id);
        return bookService.updateBook(book);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

    @GetMapping("/author") //完整请求地址：api/books/author?name=tianmin
    public List<Book> getBooksByAuther(@RequestParam String name) {
        return bookService.getBooksByAuthor(name);
    }

    @GetMapping("/price") //完整请求地址：api/books/price?min=1&max=1000
    public List<Book> getBooksByPriceRange(
            @DecimalMin("0.00") @RequestParam Double min,
            @DecimalMax("9999.99") @RequestParam Double max) {
        return bookService.getBooksByPriceRange(min, max);
    }

    @GetMapping("/books")
    public List<Book> getBooks(@RequestHeader("User-Agent") String userAgent) {
        System.out.println("User-Agent: " + userAgent);
        return bookService.getAllBooks();
    }

}
