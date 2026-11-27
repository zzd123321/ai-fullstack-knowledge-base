package com.example.product.controller;

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
    public ProductVO create(@Valid @RequestBody ProductCreateRequest request) {
        return productService.create(request);
    }

    @GetMapping
    public List<ProductVO> list() {
        return productService.listAll();
    }

    @GetMapping("/{id}")
    public ProductVO detail(@PathVariable("id") Long id) {
        return productService.getDetail(id);
    }

    @PutMapping("/{id}")
    public ProductVO update(@PathVariable("id") Long id,
                            @Valid @RequestBody ProductCreateRequest request) {
        return productService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable("id") Long id) {
        return productService.delete(id);
    }
}