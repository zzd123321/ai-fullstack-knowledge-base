package com.example.webfluxdemo.api.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MonoFluxTest {

    public static void main(String[] args) {

//        // 创建一个包含单个元素的 Mono
//        Mono<String> mono = Mono.just("Hello, WebFlux!");
//
//        // 订阅并消费数据
//        mono.subscribe(data -> System.out.println(data));
//
//        // 创建空的 Mono
//        Mono<String> empty = Mono.empty();
//
//        // 创建包含异常的 Mono
//        Mono<String> error = Mono.error(new RuntimeException("出错了"));



//        // 创建包含多个元素的 Flux
//        Flux<Integer> flux = Flux.just(1, 2, 3, 4, 5);
//
          // 订阅并消费数据
//        flux.subscribe(data -> System.out.println(data));

//        // 从集合创建
//        List<String> list = Arrays.asList("a", "b", "c");
//        Flux<String> fluxFromList = Flux.fromIterable(list);
//
//        // 创建范围序列
//        Flux<Integer> range = Flux.range(1, 10);
//



        // Flux → Mono（取第一个元素）
        Flux<Integer> flux = Flux.just(1, 2, 3);
        Mono<Integer> mono = flux.next();  // 只取第一个
        mono.subscribe(System.out::println);

        // Mono → Flux
        Mono<String> mono2 = Mono.just("hello");
        Flux<String> fluxFromMono = mono2.flux();  // 转换为 Flux
        fluxFromMono.subscribe(System.out::println);

        // 空值处理
        Mono<Object> empty = Mono.empty();
        Flux<Object> defaultFlux = empty.defaultIfEmpty("default").flux();
        defaultFlux.subscribe(System.out::println);
    }


}
