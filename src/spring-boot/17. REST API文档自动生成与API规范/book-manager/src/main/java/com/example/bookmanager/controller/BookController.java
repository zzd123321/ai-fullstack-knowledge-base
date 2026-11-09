package com.example.bookmanager.controller;

import com.example.bookmanager.model.Book;
import com.example.bookmanager.model.Result;
import com.example.bookmanager.model.ResultCode;
import com.example.bookmanager.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@Tag(name = "图书管理", description = "图书的增删改查接口")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    @Operation(summary = "查询所有图书", description = "返回系统中所有图书的列表")
    @ApiResponse(responseCode = "200", description = "查询成功",
            content = @Content(schema = @Schema(implementation = Result.class)))
    public Result<List<Book>> getAllBooks() {
        return Result.success(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询单本图书", description = "通过图书ID获取完整的图书信息")
    @Parameter(name = "id", description = "图书ID", required = true, example = "1")
    @ApiResponse(responseCode = "200", description = "查询成功")
    @ApiResponse(responseCode = "404", description = "图书不存在")
    public Result<Book> getBook(@Min(1) @PathVariable Long id) {
        return Result.success(bookService.getBookById(id));
    }

    @GetMapping("/author")
    @Operation(summary = "根据作者查询图书", description = "通过作者名称查询所有对应的图书")
    @Parameter(name = "name", description = "作者名称", required = true, example = "鲁迅")
    @ApiResponse(responseCode = "200", description = "查询成功")
    public Result<List<Book>> getBooksByAuthor(@RequestParam String name) {
        return Result.success(bookService.getBooksByAuthor(name));
    }

    @GetMapping("/price")
    @Operation(summary = "根据价格区间查询图书", description = "查询价格在指定范围内的所有图书")
    @Parameter(name = "min", description = "最低价格", required = true, example = "10.0")
    @Parameter(name = "max", description = "最高价格", required = true, example = "50.0")
    @ApiResponse(responseCode = "200", description = "查询成功")
    public Result<List<Book>> getBooksByPriceRange(
            @RequestParam @Min(0) Double min,
            @RequestParam @Min(0) Double max) {
        return Result.success(bookService.getBooksByPriceRange(min, max));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "新增图书", description = "创建一本新的图书")
    @ApiResponse(responseCode = "201", description = "创建成功")
    @ApiResponse(responseCode = "400", description = "参数校验失败")
    public Result<Book> createBook(@Valid @RequestBody Book book) {
        Book created = bookService.createBook(book);
        return Result.success(ResultCode.CREATED.getMessage(), created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新图书", description = "根据ID完整更新图书信息")
    @Parameter(name = "id", description = "图书ID", required = true, example = "1")
    @ApiResponse(responseCode = "200", description = "更新成功")
    @ApiResponse(responseCode = "404", description = "图书不存在")
    @ApiResponse(responseCode = "400", description = "参数校验失败")
    public Result<Book> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody Book book) {
        book.setId(id);
        return Result.success(bookService.updateBook(book));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "删除图书", description = "根据ID删除图书")
    @Parameter(name = "id", description = "图书ID", required = true, example = "1")
    @ApiResponse(responseCode = "204", description = "删除成功")
    @ApiResponse(responseCode = "404", description = "图书不存在")
    public Result<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return Result.success(ResultCode.NO_CONTENT.getMessage(), null);
    }
}