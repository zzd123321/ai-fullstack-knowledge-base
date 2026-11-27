package com.example.product.nacos.provider;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.Map;

public class NacosServiceRegistry {

    private final NamingService namingService;
    private final String serviceName;
    private final String groupName;
    private final String ip;
    private final int port;

    public NacosServiceRegistry(String serverAddr, String namespace, String serviceName,
                                String groupName, String ip, int port) throws NacosException {

        this.namingService = NacosClientFactory.creteNamingService(serverAddr, namespace);
        this.serviceName = serviceName;
        this.groupName = groupName;
        this.ip = ip;
        this.port = port;
    }

    // 注册实例，携带元数据
    public void register(Map<String, String> metadata) throws NacosException {
        Instance instance = new Instance();
        instance.setIp(ip);
        instance.setPort(port);
        instance.setServiceName(serviceName);
        instance.setWeight(1.0);
        instance.setMetadata(metadata);
        namingService.registerInstance(serviceName, groupName, instance);
        System.out.println("实例注册成功：" + ip + ":" + port + " 元数据：" + metadata);
    }

    //注销实例
    public void deregister() throws NacosException {
        Instance instance = new Instance();
        instance.setIp(ip);
        instance.setPort(port);
        instance.setServiceName(serviceName);
        namingService.deregisterInstance(serviceName, groupName, instance);
        System.out.println("实例已注销：" + ip + ":" + port);
    }

    // 动态更新权重
    public void updateWeight(Map<String, String> metadata, double weight) throws NacosException {
        Instance instance = new Instance();
        instance.setIp(ip);
        instance.setPort(port);
        instance.setServiceName(serviceName);
        instance.setWeight(weight);
        instance.setMetadata(metadata);
        namingService.registerInstance(serviceName, groupName, instance);
        System.out.println("权重已更新为：" + weight);
    }
}
