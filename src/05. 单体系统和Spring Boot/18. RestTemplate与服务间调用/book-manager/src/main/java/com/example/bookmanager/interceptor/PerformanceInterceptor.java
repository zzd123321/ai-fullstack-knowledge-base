package com.example.bookmanager.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class PerformanceInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(PerformanceInterceptor.class);
    private static final long SLOW_THRESHOLD = 1000;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute("_startTime", System.currentTimeMillis());
        return true;
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        // 从请求开始的环节获取变量，用于处理请求结束的响应需求
        Long startTime = (Long) request.getAttribute("_startTime");
        if(startTime != null) {
            long duration = System.currentTimeMillis() - startTime;
            String uri = request.getRequestURI();
            if(duration > SLOW_THRESHOLD) {
                logger.warn("[Interceptor] 慢接口：{} 耗时： {}ms", uri, duration);
            } else {
                logger.debug("[Interceptor] {} 耗时： {}ms", uri, duration);
            }
        }
    }

}
