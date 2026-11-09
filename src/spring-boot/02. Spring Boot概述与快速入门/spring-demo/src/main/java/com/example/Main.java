package com.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {

        // 启动Spring容器，基于配置（使用注解的方式）
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        // 从容器中获取GreetingService的实例Bean
        GreetingService service = context.getBean(GreetingService.class);

        // 调用GreetingService实例类
        System.out.println(service.greet("Alice"));
    }
}