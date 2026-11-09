package com.example.bookmanager.config;

import com.example.bookmanager.interceptor.SlowSqlInterceptor;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class MybatisConfig {

    @Bean
    public SlowSqlInterceptor slowSqlInterceptor() {
        SlowSqlInterceptor slowSqlInterceptor = new SlowSqlInterceptor();
        // 通过 Properties 设置阈值
        Properties prop = new Properties();
        prop.put("slowSqlThreshold", "1000");
        slowSqlInterceptor.setProperties(prop);
        return slowSqlInterceptor;
    }

    @Bean
    public ConfigurationCustomizer mybtisConfigurationCustomizer() {
        return configuration -> configuration.addInterceptor(slowSqlInterceptor());
    }
}
