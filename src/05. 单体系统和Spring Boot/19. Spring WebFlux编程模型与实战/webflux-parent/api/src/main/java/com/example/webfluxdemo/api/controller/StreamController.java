package com.example.webfluxdemo.api.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
public class StreamController {

    // 每秒推送一个数字，产生无限流
    @GetMapping(value = "/numbers", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Long> numbers() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(i -> i + 1)
                .take(10);  // 只取前 10 个
    }

    // 流式返回用户列表
    @GetMapping(value = "/users/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> userStream() {
        return Flux.just("Alice", "Bob", "Charlie")
                .delayElements(Duration.ofSeconds(1));  // 每秒发送一个
    }
}