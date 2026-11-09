package com.example.webfluxdemo.config;

import com.example.webfluxdemo.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> userRouter(UserHandler userHandler) {
        return route(GET("/api/users"), userHandler::getAll)
                .andRoute(GET("/api/users/{id}"), userHandler::getById)
                .andRoute(POST("/api/users"), userHandler::create)
                .andRoute(DELETE("/api/users/{id}"), userHandler::delete);
    }

    @Bean
    public RouterFunction<ServerResponse> combinedRoutes(UserHandler handler) {
        return route()
                .GET("/api/users", handler::getAll)
                .GET("/api/users/{id}", handler::getById)
                .POST("/api/users", handler::create)
                .DELETE("/api/users/{id}", handler::delete)
                .build();
    }
}
