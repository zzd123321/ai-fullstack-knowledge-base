package com.example.order.nacos.weight;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class WeightedInstanceSelector {

    public static ServiceInstance choose(List<ServiceInstance> instances) {

        if (instances == null || instances.isEmpty()) {
            return null;
        }

        // 过滤掉权重不合理的实例，权重缺失按照1.0默认值处理
        List<ServiceInstance> candidates = instances.stream()
                .filter(i -> parseWeight(i) > 0)
                .toList();

        if(candidates.isEmpty()) {
            // 相当于所有实例的权重都是0，等同于全部限流，返回无实例可选的结果
            return null;
        }

        // 计算候选实例的总权重，作为随机值的上限
        double totalWeight = candidates.stream()
                .mapToDouble(WeightedInstanceSelector::parseWeight)
                .sum();

        // 在[0, totalWeight)区间获取一个均匀分布的随机值
        double random = ThreadLocalRandom.current().nextDouble(totalWeight);

        // 按实例顺序累加权重，随机值首次小于累计值的时候就命中该实例
        double cursor = 0.0;
        for(ServiceInstance instance : candidates) {
            cursor += parseWeight(instance);
            if(random < cursor)
                return instance;
        }

        // 理论上的兜底，返回最后一个实例
        return candidates.get(candidates.size() - 1);
    }


    private static double parseWeight(ServiceInstance instance) {
        // 优先读取标准键名"weight"
        String weightStr = instance.getMetadata().get("weight");

        if(weightStr == null || weightStr.isBlank()) {
            // 兼容部分Naocs版本使用的"nacos.weight"键名
            weightStr = instance.getMetadata().get("nacos.weight");
        }

        if(weightStr == null || weightStr.isBlank()) {
            // metadata没有权重值，默认按1.0处理
            return 1.0;
        }

        try {
            return Double.parseDouble(weightStr);
        } catch (NumberFormatException e) {
            // 兜底方案，格式化失败返回默认值
            return 1.0;
        }
    }
}
