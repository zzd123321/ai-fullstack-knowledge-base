package com.example.webfluxdemo.handler;

import com.example.webfluxdemo.model.User;
import com.example.webfluxdemo.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class UserHandler {

    private final UserService userService;

    public UserHandler(UserService userService) {
        this.userService = userService;
    }

    // 查询所有用户
    public Mono<ServerResponse> getAll(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(userService.findAll(), User.class);
    }

    // 根据Id查询单个用户
    public Mono<ServerResponse> getById(ServerRequest request) {
        Long id = Long.parseLong(request.pathVariable("id"));

        // 需要考虑返回的User可能为空
        return userService.findById(id)
                .flatMap(user -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(user))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    // 创建用户
    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(User.class)
                // 调用UserService执行业务方法
                .flatMap(userService::save)
                // 把调用结果映射为ServerResponse
                .flatMap(saved -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(saved));
    }

    // 根据Id删除用户
    public Mono<ServerResponse> delete(ServerRequest request) {
        Long id = Long.parseLong(request.pathVariable("id"));
        return userService.deleteById(id)
                .then(ServerResponse.noContent().build());
    }

}
