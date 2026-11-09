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

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册权限拦截器，拦截 /api/** 路径，排除 /api/health
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/health", "/api/public/**", "/api/cache/**", "/api/demo/**");

        // 注册性能拦截器，拦截 /api/books/** 路径
        registry.addInterceptor(performanceInterceptor)
                .addPathPatterns("/api/books/**");
    }
}