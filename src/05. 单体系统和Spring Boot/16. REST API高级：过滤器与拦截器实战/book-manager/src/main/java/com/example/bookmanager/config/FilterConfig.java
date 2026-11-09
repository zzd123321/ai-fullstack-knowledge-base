package com.example.bookmanager.config;

import com.example.bookmanager.filter.LoggingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<LoggingFilter> loggingFilter() {
        FilterRegistrationBean<LoggingFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoggingFilter());
        // 配置拦截路径
        filterRegistrationBean.addUrlPatterns("/api/*");
        // 配置执行顺序（数字越小越先执行）
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }
}
