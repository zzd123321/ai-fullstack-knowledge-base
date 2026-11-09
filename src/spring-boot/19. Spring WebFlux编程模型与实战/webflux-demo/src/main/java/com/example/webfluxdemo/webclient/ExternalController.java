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

    @GetMapping("/{id}")
    public Mono<User> getUser(@PathVariable Long id) {
        return externalApiService.fetchUser(id);
    }

    @GetMapping
    public Flux<User> getAllUsers() {
        return externalApiService.fetchAllUser();
    }

    @PostMapping
    public Mono<User> createUser(@RequestBody User user) {
        return externalApiService.createUser(user);
    }

    @GetMapping("/secure/{id}")
    public Mono<User> getUserWithExceptionHandling(@PathVariable Long id) {
        return externalApiService.fetchUserWithExceptionHandling(id);
    }
}
