package com.example.order.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Component
@Order(2)
public class FeignAuthInterceptor implements RequestInterceptor {

    private static final String AUTH_HEADER = "Authorization";

    @Override
    public void apply(RequestTemplate requestTemplate) {

        log.info("进入FeignAuthInterceptor");

        // 从当前HTTP请求上下文中获取原始请求
        ServletRequestAttributes attributes =
                (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader(AUTH_HEADER);

        if(StringUtils.hasText(token)) {
            requestTemplate.header(AUTH_HEADER, token);
        }
    }
}
