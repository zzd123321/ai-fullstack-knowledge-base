package com.example.bookmanager.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//@WebFilter(urlPatterns = "/api/*")
public class SpringLoggingFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(SpringLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 记录请求信息
        long startTime = System.currentTimeMillis();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String ip = request.getRemoteAddr();

        logger.info("[Filter] 请求：{} {} IP: {}", method, uri, ip);

        // 继续执行后续过滤器或请求处理
        filterChain.doFilter(request, response);

        // 记录响应时间
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        logger.info("[Filter] 完成：{} {} 耗时: {}ms", method, uri, duration);
    }
}
