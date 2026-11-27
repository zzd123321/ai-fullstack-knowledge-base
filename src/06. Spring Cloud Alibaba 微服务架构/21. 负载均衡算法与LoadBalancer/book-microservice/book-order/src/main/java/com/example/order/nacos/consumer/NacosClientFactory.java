package com.example.order.nacos.consumer;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;

import java.util.Properties;

public class NacosClientFactory {

    public static NamingService creteNamingService(String serverAddr, String namespace) throws NacosException {
        Properties properties = new Properties();
        properties.put("serverAddr", serverAddr);
        properties.put("namespace", namespace);
        // 如果启用了鉴权机制，需要配置用户名和密码
        properties.put("username", "nacos");
        properties.put("password", "nacos");

        return NacosFactory.createNamingService(properties);
    }
}
