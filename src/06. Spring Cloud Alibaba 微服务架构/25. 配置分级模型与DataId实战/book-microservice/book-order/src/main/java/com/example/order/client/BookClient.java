package com.example.order.client;

import com.example.common.feign.BookApi;
import com.example.common.feign.dto.ProductDto;
import com.example.common.feign.dto.ProductQuery;
import com.example.common.feign.dto.StockDeductRequest;
import com.example.common.model.Result;
import com.example.order.config.DefaultFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "book-product", configuration = DefaultFeignConfig.class, path = "/api")
public interface BookClient extends BookApi {

    // 按照条件查询图书
    @GetMapping("/products/query")
    Result<List<ProductDto>> query(@SpringQueryMap ProductQuery query);

    // 扣减库存
    @PostMapping("/stock/deduct")
    Result<Void> deductStock(@RequestBody StockDeductRequest request);

    @GetMapping("/products/{id}/auth")
    Result<ProductDto> getByIdWithAuth(@PathVariable("id") Long id);

}
