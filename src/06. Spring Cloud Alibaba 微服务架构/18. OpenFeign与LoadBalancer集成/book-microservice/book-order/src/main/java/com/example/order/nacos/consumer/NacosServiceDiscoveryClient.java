package com.example.order.nacos.consumer;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.listener.EventListener;
import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;

public class NacosServiceDiscoveryClient {

    private final NamingService namingService;
    private final String serviceName;
    private final String groupName;

    public NacosServiceDiscoveryClient(String serverAddr, String namespace,
                                       String serviceName, String groupName) throws NacosException {
        this.namingService = NacosClientFactory.creteNamingService(serverAddr, namespace);
        this.serviceName = serviceName;
        this.groupName = groupName;
    }

    // 订阅服务的变化
    public void subscribe(EventListener listener) throws NacosException {
        namingService.subscribe(serviceName, groupName, listener);
    }

    // 获取所有的健康实例
    public List<Instance> getAllHealthyInstances() throws NacosException {
        return namingService.selectInstances(serviceName, groupName, true);
    }

    // 加权随机获取一个健康实例（Nacos内置方法）
    public Instance selectOneHealthyInstance() throws NacosException {
        return namingService.selectOneHealthyInstance(serviceName, groupName);
    }
}
