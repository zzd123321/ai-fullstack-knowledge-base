package com.example.product.controller;

import com.example.common.model.Result;
import com.example.product.config.BookProductProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/config")
@RefreshScope
public class ConfigDemoController {

    @Autowired
    private BookProductProperties productProperties;

    @Value("${book.product.page-size:10}")
    private Integer pageSize;

    @GetMapping("/page-size")
    public Result<Integer> getPageSize() {
        return Result.success(pageSize);
    }

    @GetMapping("/properties")
    public Result<Integer> getProperties() {
        return Result.success(productProperties.getPageSize());
    }
}
