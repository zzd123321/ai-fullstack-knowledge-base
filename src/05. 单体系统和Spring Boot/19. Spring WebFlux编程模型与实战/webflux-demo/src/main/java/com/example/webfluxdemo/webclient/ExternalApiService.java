package com.example.webfluxdemo.webclient;

import com.example.webfluxdemo.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ExternalApiService {

    private final WebClient webClient;

    public ExternalApiService(WebClient webClient) {
        this.webClient = webClient;
    }

    //GET请求
    public Mono<User> fetchUser(Long id) {
        return webClient.get()
                .uri("/users/{id}", id)
                .retrieve()
                .bodyToMono(User.class);
    }

    //GET请求
    public Flux<User> fetchAllUser() {
        return webClient.get()
                .uri("/users")
                .retrieve()
                .bodyToFlux(User.class);
    }

    //POST请求
    public Mono<User> createUser(User user) {
        return webClient.post()
                .uri("/users")
                .bodyValue(user)
                .retrieve()
                .bodyToMono(User.class);
    }

    //GET请求（带异常）
    public Mono<User> fetchUserWithExceptionHandling(Long id) {
        return webClient.get()
                .uri("/users/{id}", id)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(),
                        clientResponse -> Mono.error(new RuntimeException("用户不存在")))
                .bodyToMono(User.class);
    }

}
