package com.example.gateway.handler;

import com.example.common.enums.ResultCode;
import com.example.common.model.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.cloud.gateway.support.TimeoutException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class GatewayExceptionHandler implements ErrorWebExceptionHandler {

    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();

        // 如果响应已经提交（如流式响应已开始），无法再写入错误体，直接返回
        if(response.isCommitted()) {
            log.warn("[网关异常] 响应已提交，无法处理异常：{}", ex.getMessage());
            return Mono.error(ex);
        }

        String path = exchange.getRequest().getPath().value();
        HttpStatus httpStatus;
        Integer code;
        String message;

        // 1. 路由未匹配（404）
        if(ex instanceof ResponseStatusException rse
        && rse.getStatusCode().value() == 404) {
            httpStatus = HttpStatus.NOT_FOUND;
            code = ResultCode.NOT_FOUND.getCode();
            message = "请求的资源不存在：" + path;

            // 2. 下游服务不可用（503）
        } else if(ex instanceof NotFoundException) {
            httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
            code = ResultCode.SERVICE_UNAVAILABLE.getCode();
            message = "下游服务暂时不可用，请稍后重试";

            // 3. 下游超时（504）
        } else if(ex instanceof TimeoutException
            || ex.getCause() instanceof TimeoutException) {
            httpStatus = HttpStatus.GATEWAY_TIMEOUT;
            code = 504;
            message = "下游服务响应超时，请稍后重试";

            // 4. 其他未预期异常（500）
        } else {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            code = ResultCode.INTERNAL_ERROR.getCode();
            message = "网关内部错误，请稍后重试";

            log.error("[网关异常] 未预期异常，path={}", path, ex);
        }

        log.warn("[网关异常] path={}, status={}, message={}", path, httpStatus.value(), message);

        // 写响应
        response.setStatusCode(httpStatus);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        Result<Void> body = Result.error(code, message);
        byte[] bytes;
        try {
            bytes = objectMapper.writeValueAsBytes(body);
        } catch (JsonProcessingException e) {
            bytes = ("{\"code\":" + code + ",\"message\":\"" + message + "\"}").getBytes(StandardCharsets.UTF_8);
        }

        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        return response.writeWith(Mono.just(buffer));
    }
}
