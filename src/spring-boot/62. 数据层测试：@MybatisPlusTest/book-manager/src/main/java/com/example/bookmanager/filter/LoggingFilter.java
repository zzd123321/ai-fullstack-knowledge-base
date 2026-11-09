package com.example.bookmanager.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("LoggingFilter 初始化");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // 记录请求信息
        long startTime = System.currentTimeMillis();
        String method = httpRequest.getMethod();
        String uri = httpRequest.getRequestURI();
        String ip = httpRequest.getRemoteAddr();

        logger.info("📨 [Filter] 请求: {} {} | IP: {}", method, uri, ip);

        // 继续执行后续过滤器或请求处理
        chain.doFilter(request, response);

        // 记录响应耗时
        long duration = System.currentTimeMillis() - startTime;
        logger.info("✅ [Filter] 完成: {} {} | 耗时: {}ms", method, uri, duration);
    }

    @Override
    public void destroy() {
        logger.info("LoggingFilter 销毁");
    }
}