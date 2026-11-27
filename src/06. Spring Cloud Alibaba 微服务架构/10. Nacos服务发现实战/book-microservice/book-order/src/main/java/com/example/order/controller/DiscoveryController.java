package com.example.order.controller;

import com.example.common.model.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/discovery")
@RequiredArgsConstructor
public class DiscoveryController {

    private final DiscoveryClient discoveryClient;

    private final RestTemplate restTemplate;

    //1. 获取所有已注册的服务名列表
    @GetMapping("/services")
    public Result<List<String>> getServices() {
        List<String> services = discoveryClient.getServices();
        log.info("当前注册的服务列表： {}", services);
        return Result.success(services);
    }

    // 2. 获取指定服务的所有实例
    @GetMapping("/instances/{serviceName}")
    public Result<List<ServiceInstance>> getInstances(@PathVariable("serviceName") String serviceName) {
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceName);
        log.info("服务 {} 的实例列表：{}", serviceName, instances);
        return Result.success(instances);
    }

    // 2. 获取book-product服务的详细实例信息
    @GetMapping("/book-instances")
    public Result<List<ServiceInstance>> getBookInstances() {
        List<ServiceInstance> instances = discoveryClient.getInstances("book-product");
        if (instances == null || instances.isEmpty()) {
            log.warn("未发现任何book-product的实例");
        } else {
            for (ServiceInstance instance : instances) {
                log.info("服务Id：{}， 主机：{}， 端口：{}， URI：{}， 元数据：{}",
                        instance.getServiceId(),
                        instance.getHost(),
                        instance.getPort(),
                        instance.getUri(),
                        instance.getMetadata());
            }
        }

        return Result.success(instances);
    }

    @GetMapping("/book/{id}")
    public Result<String> getBookById(@PathVariable("id") Long id) {

        // 1. 通过服务发现后去图书服务的实例列表
        List<ServiceInstance> instances = discoveryClient.getInstances("book-product");
        if (instances == null || instances.isEmpty()) {
            return Result.error(503, "图书服务暂时不可用，请稍后重试");
        }

        // 2. 选择一个实例，本课程选择第一个，后续我们会加入负载均衡策略
        ServiceInstance instance = instances.get(0);
        String baseUrl = instance.getUri().toString();

        log.info("选中图书服务实例：{}：{}", instance.getHost(), instance.getPort());

        // 3. 构建完整URL并发起调用
        String requestUrl = baseUrl + "/api/products/" + id;
        log.info("发起远程调用：{}", requestUrl);

        try{
            String result = restTemplate.getForObject(requestUrl, String.class);
            return Result.success(result);
        } catch(Exception e) {
            log.error("调用图书服务失败", e);
            return Result.error(500, "调用图书服务失败：" + e.getMessage());
        }
    }
}
