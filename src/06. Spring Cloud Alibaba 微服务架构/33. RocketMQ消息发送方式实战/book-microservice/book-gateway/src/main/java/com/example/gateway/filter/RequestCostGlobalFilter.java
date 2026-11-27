package com.example.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class RequestCostGlobalFilter implements GlobalFilter, Ordered {
    private static final String START_TIME_ATTR = "requestStartTime";
    private static final long SLOW_THRESHOLD_MS = 200L;
    private static final long VERY_SLOW_THRESHOLD_MS = 1000L;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // 在上下文属性中添加自定义业务变量
        exchange.getAttributes().put(START_TIME_ATTR, System.currentTimeMillis());

        return chain.filter(exchange).doFinally(signalType -> {

            // 从属性中获取业务变量
            Long startTime = exchange.getAttribute(START_TIME_ATTR);
            long cost = System.currentTimeMillis() - startTime;
            ServerHttpRequest request = exchange.getRequest();
            String method = request.getMethod().name();
            String path = request.getPath().value();

            if(cost > VERY_SLOW_THRESHOLD_MS) {
                log.error("[极慢请求] {} {} 耗时={}", method, path, cost);
                exchange.getResponse().beforeCommit(() -> {
                    //修改响应头
                    exchange.getResponse().getHeaders().add("X-Slow-Request", "true");
                    return Mono.empty();
                });
            } else if(cost > SLOW_THRESHOLD_MS) {
                log.warn("[慢请求] {} {} 耗时={}", method, path, cost);
            } else {
                log.debug("[正常请求] {} {} 耗时={}", method, path, cost);
            }

        });
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 200;
    }
}
