package com.example.webfluxdemo.webclient;

import com.example.webfluxdemo.model.User;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/client")
public class ExternalController {

    private final ExternalApiService externalApiService;

    public ExternalController(ExternalApiService externalApiService) {
        this.externalApiService = externalApiService;
    }

    // 获取单个用户（无显式错误处理，由全局异常处理器或默认处理）
    @GetMapping("/{id}")
    public Mono<User> getUser(@PathVariable Long id) {
        return externalApiService.fetchUser(id);
    }

    // 获取所有用户
    @GetMapping
    public Flux<User> getAllUsers() {
        return externalApiService.fetchAllUsers();
    }

    // 创建用户
    @PostMapping
    public Mono<User> createUser(@RequestBody User user) {
        return externalApiService.createUser(user);
    }

    // 获取单个用户（带自定义错误处理，当用户不存在时抛出 RuntimeException）
    @GetMapping("/secure/{id}")
    public Mono<User> getUserWithErrorHandling(@PathVariable Long id) {
        return externalApiService.fetchUserWithErrorHandling(id);
    }
}