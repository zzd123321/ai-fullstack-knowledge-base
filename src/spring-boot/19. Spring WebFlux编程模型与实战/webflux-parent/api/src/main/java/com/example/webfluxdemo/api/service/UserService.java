package com.example.webfluxdemo.api.service;

import com.example.webfluxdemo.model.User;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {

    private final Map<Long, User> store = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Flux<User> findAll() {
        // 返回所有用户（Flux）
        return Flux.fromIterable(store.values());
    }

    public Mono<User> findById(Long id) {
        // 根据 ID 查找（Mono），可能为空
        return Mono.justOrEmpty(store.get(id));
    }

    public Mono<User> save(User user) {
        // 保存用户
        user.setId(idGenerator.getAndIncrement());
        store.put(user.getId(), user);
        return Mono.just(user);
    }

    public Mono<User> update(User user) {
        if (store.containsKey(user.getId())) {
            store.put(user.getId(), user);
            return Mono.just(user);
        }
        return Mono.empty();  // 或抛出异常
    }

    public Mono<Void> deleteById(Long id) {
        store.remove(id);
        return Mono.empty();  // 返回空 Mono 表示完成
    }
}