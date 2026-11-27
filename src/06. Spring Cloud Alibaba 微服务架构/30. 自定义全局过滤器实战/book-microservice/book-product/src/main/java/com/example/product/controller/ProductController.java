package com.example.product.controller;

import com.example.common.feign.BookApi;
import com.example.common.feign.dto.ProductDto;
import com.example.common.feign.dto.ProductQuery;
import com.example.common.model.Result;
import com.example.product.model.dto.ProductCreateRequest;
import com.example.product.service.ProductService;
import com.example.product.service.vo.ProductVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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
    public Result<ProductVO> detail(@PathVariable("id") Long id, HttpServletRequest request) {
        log.info("book-product实例{}被调用", port);

        System.out.println("path:" + request.getRequestURI());
        System.out.println("X-Request-Source:" + request.getHeader("X-Request-Source"));
        System.out.println("authorization:" + request.getHeader("Authorization"));

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

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

    @GetMapping("/query")
    public Result<List<ProductDto>> query(ProductQuery query) {

        return Result.success(productService.query(query));
    }

    @GetMapping("/search")
    public Result<List<ProductDto>> search(@RequestParam("keyword") String keyword) {

        return Result.success(Collections.emptyList());
    }

    @Override
    @GetMapping("/query/{id}")
    public Result<ProductDto> findProductById(@PathVariable("id") Long id) {
        return Result.success(productService.getBookById(id));
    }

    @GetMapping("/{id}/auth")
    public Result<ProductDto> getByIdWithAuth(@PathVariable("id") Long id,
            @RequestHeader(value = "Authorization", required = false) String token,
            @RequestHeader(value = "X-Trace-Id", required = false) String traceId) {

        log.info("收到的Token：{}", token);
        log.info("收到的TraceId：{}", traceId);
        return Result.success(productService.getBookById(id));
    }
}