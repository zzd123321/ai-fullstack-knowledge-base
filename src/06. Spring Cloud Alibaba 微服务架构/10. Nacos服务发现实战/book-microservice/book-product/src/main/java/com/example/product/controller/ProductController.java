package com.example.product.controller;

import com.example.common.model.Result;
import com.example.product.model.dto.ProductCreateRequest;
import com.example.product.service.ProductService;
import com.example.product.service.vo.ProductVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

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
}