package com.example.order.nacos.consumer;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.listener.NamingEvent;
import com.alibaba.nacos.api.naming.pojo.Instance;

public class BookOrderService {

    public static void main(String[] args) throws NacosException, InterruptedException {
        NacosServiceDiscoveryClient client = new NacosServiceDiscoveryClient(
                "127.0.0.1:8848",
                "dev",
                "book-product",
                "DEFAULT_GROUP"
        );

        // 注册监听器：实例列表变化时自动触发
        client.subscribe(event -> {
            if(event instanceof NamingEvent namingEvent) {
                System.out.println("服务" + namingEvent.getServiceName()
                    + "实例列表已更新，当前实例数：" + namingEvent.getInstances().size());

                for(Instance instance : namingEvent.getInstances()) {
                    System.out.println("实例：" + instance.getIp() + ":" + instance.getPort()
                        + "健康=" + instance.isHealthy()
                        + "权重=" + instance.getWeight()
                        + "启用=" + instance.isEnabled());
                }
            }
        });

        // 模拟业务调用：每3秒选择一个实例
        while (true) {
            Instance instance = client.selectOneHealthyInstance();
            System.out.println("选中实例：" + instance.getIp() + ":" + instance.getPort()
                    + "权重=" + instance.getWeight());

            // 使用选中的实例发起请求（模拟）
            String url = "http://" + instance.getIp() + ":" + instance.getPort() + "/api/products/1";
            System.out.println(url);

            Thread.sleep(3000);
        }

    }
}
