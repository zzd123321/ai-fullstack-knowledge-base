package com.example.bookmanager.config;

import com.example.bookmanager.interceptor.AsyncRequestInterceptor;
import com.example.bookmanager.interceptor.AuthInterceptor;
import com.example.bookmanager.interceptor.PerformanceInterceptor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;

    @Autowired
    private PerformanceInterceptor performanceInterceptor;

    @Autowired
    private AsyncRequestInterceptor asyncRequestInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册权限拦截器，拦截 /api/** 路径，排除 /api/health
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/health", "/api/public/**", "/api/cache/**", "/api/demo/**");

        // 注册性能拦截器，拦截 /api/books/** 路径
        registry.addInterceptor(performanceInterceptor)
                .addPathPatterns("/api/books/**");

        // 注册异步执行拦截器，拦截 /api/async/** 路径
        registry.addInterceptor(asyncRequestInterceptor)
                .addPathPatterns("/api/async/**");
    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        configurer.setDefaultTimeout(30000); // 默认超时30秒：全局设置
        configurer.setTaskExecutor(asyncTaskExecutor()); // 覆写了默认的TaskExecutor
    }

    @Bean(value = "webAsyncTaskExecutor")
    public AsyncTaskExecutor asyncTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("web-async-");
        executor.initialize();
        return executor;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}