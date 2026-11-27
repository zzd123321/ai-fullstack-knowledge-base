package com.example.product.nacos.provider;

import com.alibaba.nacos.api.exception.NacosException;

import java.util.HashMap;
import java.util.Map;

public class BookProductApplication {

    private static NacosServiceRegistry registry;

    public static void main(String[] args) throws NacosException, InterruptedException {
        registry = new NacosServiceRegistry(
                "127.0.0.1:8848",
                "dev",
                "book-product",
                "DEFAULT_GROUP",
                "127.0.0.1",
                8084
        );

        // 上报元数据
        Map<String, String> metadata = new HashMap<>();
        metadata.put("version", "1.0.0");
        metadata.put("region", "beijing");
        metadata.put("env", "dev");
        registry.register(metadata);

        // 根据模拟的CPU负载，动态调整权重
        new Thread(() -> {
            while (true) {
                try {
                    double cpuLoad = Math.random(); // 模拟CPU的负载
                    double weight = cpuLoad > 0.6 ? 0.2 : 1.0;
                    registry.updateWeight(metadata, weight);
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // 模拟服务关闭时注销实例
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                registry.deregister();
            } catch (NacosException e) {
                e.printStackTrace();
            }
        }));

        //保持服务运行
        Thread.sleep(Long.MAX_VALUE);
    }
}
