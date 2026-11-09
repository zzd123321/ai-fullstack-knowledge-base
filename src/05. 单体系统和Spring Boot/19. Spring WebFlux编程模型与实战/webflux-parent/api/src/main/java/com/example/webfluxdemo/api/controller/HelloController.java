package com.example.webfluxdemo.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public Mono<String> hello() {
        // 返回 0-1 个元素（单个字符串）
        return Mono.just("Hello, Spring WebFlux!");
    }

    @GetMapping("/users")
    public Flux<String> users() {
        // 返回 0-N 个元素（字符串列表）
        return Flux.just("Alice", "Bob", "Charlie", "Diana");
    }

    @GetMapping("/users/{id}")
    public Mono<String> getUser(@PathVariable Long id) {
        // 根据 ID 返回单个用户
        return Mono.just("User-" + id);
    }
}