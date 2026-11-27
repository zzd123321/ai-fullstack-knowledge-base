package com.example.order.controller;

import com.example.common.feign.dto.ProductDto;
import com.example.common.feign.dto.ProductQuery;
import com.example.common.feign.dto.StockDeductRequest;
import com.example.common.model.Result;
import com.example.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderProductController {

    private final OrderService orderService;

    @GetMapping("/products/query/{id}")
    Result<ProductDto> getProductById(@PathVariable("id") Long id) {
        return Result.success(orderService.getProductById(id));
    }

    @GetMapping("/products/query")
    Result<List<ProductDto>> query(ProductQuery query) {
        return Result.success(orderService.query(query));
    }

    @PostMapping("/stock/deduct")
    Result<Void> deductStock(@RequestBody StockDeductRequest request) {
        orderService.deductStock(request.getProductId(), request.getQuantity());
        return Result.success();
    }

    @GetMapping("/products/{id}/auth")
    Result<ProductDto> getProductWithAuth(@PathVariable("id") Long id,
                                          @RequestHeader("Authorization") String token) {
        // 拦截器会自动将当前请求的Authorization头注入到Feigh请求
        return Result.success(orderService.getProductById(id));
    }
}
