package com.example.product.controller;

import com.example.common.feign.BookApi;
import com.example.common.feign.dto.ProductDto;
import com.example.common.feign.dto.ProductQuery;
import com.example.common.model.Result;
import com.example.product.model.dto.ProductCreateRequest;
import com.example.product.service.ProductService;
import com.example.product.service.vo.ProductVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController implements BookApi {

    @Value("${server.port}")
    private String port;

    private final ProductService productService;

    @PostMapping
    public Result<ProductVO> create(@Valid @RequestBody ProductCreateRequest request) {
        return Result.success(productService.create(request));
    }

    @GetMapping
    public Result<List<ProductVO>> list() {
        return Result.success(productService.listAll());
    }

    @GetMapping("/{id}")
    public Result<ProductVO> detail(@PathVariable("id") Long id) {
        log.info("book-product实例{}被调用", port);

        return Result.success(productService.getDetail(id));
    }

    @PutMapping("/{id}")
    public Result<ProductVO> update(@PathVariable("id") Long id,
                                    @Valid @RequestBody ProductCreateRequest request) {
        return Result.success(productService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable("id") Long id) {
        return Result.success(productService.delete(id));
    }

    @GetMapping("/instance-port")
    public String instancePort() {
        log.info("book-product实例{}被调用 instance-port", port);

        return port;
    }

//    @GetMapping("/query/{id}")
//    public Result<ProductDto> getBookById(@PathVariable("id") Long id) {
//
//        return Result.success(productService.getBookById(id));
//    }

    @GetMapping("/query")
    public Result<List<ProductDto>> query(ProductQuery query) {

        return Result.success(productService.query(query));
    }

    @Override
    @GetMapping("/query/{id}")
    public Result<ProductDto> findById(@PathVariable("id") Long id) {
        return Result.success(productService.getBookById(id));
    }
}