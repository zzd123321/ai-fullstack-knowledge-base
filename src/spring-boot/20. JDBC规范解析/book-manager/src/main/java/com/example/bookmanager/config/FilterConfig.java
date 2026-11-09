package com.example.bookmanager.config;

import com.example.bookmanager.filter.LoggingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<LoggingFilter> loggingFilter() {
        FilterRegistrationBean<LoggingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new LoggingFilter());
        // 配置拦截路径
        registrationBean.addUrlPatterns("/api/*");
        // 配置执行顺序（数字越小越先执行）
        registrationBean.setOrder(1);
        return registrationBean;
    }
}