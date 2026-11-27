package com.example.order.loadbalancer;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.EmptyResponse;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class GrayTagLoadBalancer implements ReactorServiceInstanceLoadBalancer {

    // 请求头中的灰度标签
    private static final String GRAY_HEADER = "X-Gray-Tag";

    // 实例元数据中的版本键
    private static final String VERSION_KEY = "version";

    private final String serviceId;
    private final ObjectProvider<ServiceInstanceListSupplier> supplierProvider;

    // 每个实例的当前权重，key=ip:port（注意线程安全性）
    private final Map<String, AtomicInteger> currentWeights = new ConcurrentHashMap<>();

    public GrayTagLoadBalancer(String serviceId,
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

        // 1. 读取请求头中的灰度标签
        String grayTag = extractGrayTag();

        // 2. 按照标签进行过滤实例
        List<ServiceInstance> candidates = new ArrayList<>(instances);
        if(grayTag != null && !grayTag.isBlank()) {
            List<ServiceInstance> matched = instances.stream()
                    .filter(i -> grayTag.equals(i.getMetadata().get(VERSION_KEY)))
                    .collect(Collectors.toList());

            // 3. 过滤结果为空时降级到全部实例
            if(!matched.isEmpty()) {
                candidates = matched;
            } else {
                System.out.println("[Gra] 标签 " + grayTag + "无匹配的实例，降级到全部实例");
            }
        }

        // 4. 在候选实例中做平滑加权选择
        return selectByWeight(candidates);
    }

    private String extractGrayTag() {
        // 从当前的HTTP请求上下文中获取消息头
        RequestAttributes atts = RequestContextHolder.getRequestAttributes();
        if(atts instanceof ServletRequestAttributes servletRequestAttributes) {
            String version = servletRequestAttributes.getRequest().getHeader(GRAY_HEADER);
            if(version != null && !version.isEmpty()) {
                return version;
            }
        }

        return null;
    }

    private Response<ServiceInstance> selectByWeight(List<ServiceInstance> instances) {

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

//        System.out.println("[SWRR] 选中" + keyOf(selected)
//                + "weight=" + getWeight(selected)
//                + "currentWeight=" + currentWeights.get(keyOf(selected)).get());

        System.out.println("[GRAY] 选中"  + keyOf(selected)
            + " version=" + selected.getMetadata().get(VERSION_KEY));

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
