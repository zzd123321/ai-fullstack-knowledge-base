package com.example.bookmanager.controller;

import com.example.bookmanager.model.Book;
import com.example.bookmanager.model.Result;
import com.example.bookmanager.model.ResultCode;
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
        boolean saved = bookService.save(book);
        return saved ? Result.success(book) : Result.error(ResultCode.INTERNAL_ERROR, "保存异常");
    }

    @GetMapping("/{id}")
    public Result<Book> get(@PathVariable Long id) {
        Book book = bookService.getById(id);
        return book != null? Result.success(book) : Result.error(ResultCode.NOT_FOUND, "图书不存在");
    }

    @GetMapping
    public Result<List<Book>> list() {
        return Result.success(bookService.list());
    }

    @PutMapping("/{id}")
    public Result<Book> update(@PathVariable Long id, @RequestBody Book book) {
        book.setId(id);

        boolean updated = bookService.updateById(book);
        return updated ? Result.success(book) : Result.error(ResultCode.INTERNAL_ERROR, "更新异常");
    }

    @PutMapping("/version/{id}")
    public Result<Book> updateWithVersion(@PathVariable Long id, @RequestBody Book book) {
        book.setId(id);

        boolean updated = bookService.updateBookWithVersion(book);
        return updated ? Result.success(book) : Result.error(ResultCode.INTERNAL_ERROR, "更新异常");
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        boolean removed = bookService.removeById(id);
        return removed ? Result.success() : Result.error(ResultCode.INTERNAL_ERROR, "删除失败");
    }

//    @GetMapping("/search")
//    public Result<List<Book>> search(
//            @RequestParam(required = false) String author,
//            @RequestParam(required = false) String category,
//            @RequestParam(required = false) Double minPrice,
//            @RequestParam(required = false) Double maxPrice) {
//        return Result.success(bookService.searchBooks(author, category, minPrice, maxPrice));
//    }
//
//    @GetMapping("/complex")
//    public Result<List<Book>> complexSearch(
//            @RequestParam(required = false) String keyword,
//            @RequestParam(required = false) String category,
//            @RequestParam(required = false) Double minPrice,
//            @RequestParam(required = false) Double maxPrice) {
//        return Result.success(bookService.complexSearch(keyword, category, minPrice, maxPrice));
//    }
//
//    @GetMapping("/title")
//    public Result<Book> findByTitle(@RequestParam String title) {
//        return Result.success(bookService.findOneByTitle(title));
//    }
//
//    @GetMapping("/count")
//    public Result<Long> countByCategory(@RequestParam String category) {
//        return Result.success(bookService.countByCategory(category));
//    }
//
//    @PutMapping("/price/batch")
//    public Result<Integer> updatePriceByCategory(
//            @RequestParam String category,
//            @RequestParam Double multiplier) {
//        int affected = bookService.updatePriceByCategory(category, multiplier);
//        return Result.success(affected);
//    }
//
//    @GetMapping("/page")
//    public Result<Page<Book>> page(
//            @RequestParam(defaultValue = "1") Integer pageNum,
//            @RequestParam(defaultValue = "10") Integer pageSize,
//            @RequestParam(required = false) String author,
//            @RequestParam(required = false) String category) {
//        Page<Book> page = bookService.getBooksByPage(pageNum, pageSize, author, category);
//        return Result.success(page);
//    }
}