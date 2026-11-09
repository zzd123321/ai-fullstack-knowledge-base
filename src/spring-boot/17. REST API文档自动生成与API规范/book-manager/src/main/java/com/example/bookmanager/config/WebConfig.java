package com.example.bookmanager.config;

import com.example.bookmanager.interceptor.AuthInterceptor;
import com.example.bookmanager.interceptor.PerformanceInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;

    @Autowired
    private PerformanceInterceptor performanceInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {

        // 注册权限拦截器，拦截/api/**路径，排除特定的URI：/api/health
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/health", "/api/public/**");

        // 注册性能拦截器，拦截 /api/books/**
        registry.addInterceptor(performanceInterceptor)
                .addPathPatterns("/api/books/**");
    }
}
