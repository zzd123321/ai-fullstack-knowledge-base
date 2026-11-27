package com.example.order;

import com.example.order.config.RandomLoadBalancerConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.example")
@EnableDiscoveryClient
@EnableFeignClients
//@LoadBalancerClient(name = "book-product", configuration = RandomLoadBalancerConfig.class)
@LoadBalancerClients({
        @LoadBalancerClient(name = "book-product", configuration = RandomLoadBalancerConfig.class),
        @LoadBalancerClient(name = "book-user", configuration = RandomLoadBalancerConfig.class),
})
@MapperScan("com.example.order.mapper")
public class BookOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookOrderApplication.class, args);
    }
}