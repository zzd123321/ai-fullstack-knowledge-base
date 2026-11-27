package com.example.common.feign;

import com.example.common.feign.dto.ProductDto;
import com.example.common.model.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface BookApi {

    @GetMapping("/products/query/{id}")
    Result<ProductDto> findProductById(@PathVariable("id") Long id);
}
