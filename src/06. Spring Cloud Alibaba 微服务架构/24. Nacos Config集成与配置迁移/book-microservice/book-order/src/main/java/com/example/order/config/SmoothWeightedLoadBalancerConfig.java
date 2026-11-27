package com.example.order.config;

import com.example.order.loadbalancer.SmoothWeightedRoundRobinLoadBalancer;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.RandomLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

public class SmoothWeightedLoadBalancerConfig {

    @Bean
    public ReactorServiceInstanceLoadBalancer smoothWeightedLoadBalancer(
            Environment environment,
            LoadBalancerClientFactory loadBalancerClientFactory) {
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);

        return new SmoothWeightedRoundRobinLoadBalancer(
                name,
                loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class)
        );
    }
}
