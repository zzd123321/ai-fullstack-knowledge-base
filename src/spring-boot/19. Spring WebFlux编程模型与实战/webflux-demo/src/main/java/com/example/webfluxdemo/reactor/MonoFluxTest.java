package com.example.webfluxdemo.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MonoFluxTest {

    public static void main(String[] args) {

        // Flux -》 Mono（取第一个元素）
        Flux<Integer> flux = Flux.just(1, 2, 3);
        Mono<Integer> mono = flux.next();
        mono.subscribe(System.out::println);
    }

}
