package com.example.product.controller;

import com.example.common.feign.dto.StockDeductRequest;
import com.example.common.model.Result;
import com.example.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/stock")
@RequiredArgsConstructor
public class StockController {

    private final ProductService productService;

    @PostMapping("/deduct")
    public Result<Void> deductStock(@RequestBody StockDeductRequest request) {
        productService.deductStock(request.getProductId(), request.getQuantity());
        return Result.success();
    }
}
