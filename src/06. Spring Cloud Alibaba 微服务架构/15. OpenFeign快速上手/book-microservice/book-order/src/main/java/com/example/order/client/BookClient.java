package com.example.order.client;

import com.example.common.model.Result;
import com.example.order.config.DefaultFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "book-product", configuration = DefaultFeignConfig.class)
public interface BookClient {

    @GetMapping("/api/products/{id}")
    Result<Object> findById(@PathVariable("id") Long id);
}
