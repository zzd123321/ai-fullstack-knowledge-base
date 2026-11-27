package com.example.order.config;

import com.example.order.loadbalancer.GrayTagLoadBalancer;
import com.example.order.loadbalancer.SmoothWeightedRoundRobinLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

public class GrayTagLoadBalancerConfig {

    @Bean
    public ReactorServiceInstanceLoadBalancer smoothWeightedLoadBalancer(
            Environment environment,
            LoadBalancerClientFactory loadBalancerClientFactory) {
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);

        return new GrayTagLoadBalancer(
                name,
                loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class)
        );
    }
}
