package com.example.order.loadbalancer;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.EmptyResponse;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class SmoothWeightedRoundRobinLoadBalancer implements ReactorServiceInstanceLoadBalancer {

    private final String serviceId;
    private final ObjectProvider<ServiceInstanceListSupplier> supplierProvider;

    // 每个实例的当前权重，key=ip:port（注意线程安全性）
    private final Map<String, AtomicInteger> currentWeights = new ConcurrentHashMap<>();

    public SmoothWeightedRoundRobinLoadBalancer(String serviceId,
         ObjectProvider<ServiceInstanceListSupplier> supplierProvider) {
        this.serviceId = serviceId;
        this.supplierProvider = supplierProvider;
    }

    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        ServiceInstanceListSupplier supplier = supplierProvider.getIfAvailable();

        return supplier.get(request).next().map(this::select);
    }

    private Response<ServiceInstance> select(List<ServiceInstance> instances) {

        if(instances == null || instances.isEmpty()) {
            return new EmptyResponse();
        }

        // 1. 清理已下线实例的 currentWeight， 防止Map无限增长
        currentWeights.keySet().removeIf(key -> instances.stream().noneMatch(i -> keyOf(i).equals(key)));

        // 2. 累加：每个实例的currentWeight += weight
        int totalWeight = 0;
        for (ServiceInstance instance : instances) {
            int weight = getWeight(instance);
            if(weight <= 0) {
                continue;
            }

            totalWeight += weight;
            currentWeights.computeIfAbsent(keyOf(instance), k -> new AtomicInteger(0)).addAndGet(weight);
        }

        if(totalWeight <= 0)
            return new EmptyResponse();

        // 3. 选出currentWeight最大的实例
        ServiceInstance selected = null;
        int maxCurrent = Integer.MIN_VALUE;

        for (ServiceInstance instance : instances) {
            int weight = getWeight(instance);
            if(weight <= 0) {
                continue;
            }

            int current = currentWeights.get(keyOf(instance)).get();
            if(current > maxCurrent) {
                maxCurrent = current;
                selected = instance;
            }
        }

        if(selected == null) {
            return new EmptyResponse();
        }

        // 4. 被选中的实例currentWeight -= totalWeight
        currentWeights.get(keyOf(selected)).addAndGet(-totalWeight);

        System.out.println("[SWRR] 选中" + keyOf(selected)
        + "weight=" + getWeight(selected)
        + "currentWeight=" + currentWeights.get(keyOf(selected)).get());

        return new DefaultResponse(selected);
    }

    private String keyOf(ServiceInstance instance) {
        return instance.getHost() + ":" + instance.getPort();
    }

    private int getWeight(ServiceInstance instance) {
        String weight = instance.getMetadata().get("nacos.weight");
        if (weight == null) {
            return 1;
        }

        try {
            return (int)Math.round(Double.parseDouble(weight));
        } catch (NumberFormatException e) {
            return 1;
        }
    }
}
