package com.example.bookmanager.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

//@Component
public class LoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("LoggingFilter 初始化");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        System.out.println("进入过滤器");

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        // 记录请求信息
        long startTime = System.currentTimeMillis();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String ip = request.getRemoteAddr();

        logger.info("[Filter] 请求：{} {} IP: {}", method, uri, ip);

        // 继续执行后续过滤器或请求处理
        filterChain.doFilter(servletRequest, servletResponse);

        // 记录响应时间
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        logger.info("[Filter] 完成：{} {} 耗时: {}ms", method, uri, duration);
    }

    @Override
    public void destroy() {
        logger.info("LoggingFilter 销毁");
    }
}
