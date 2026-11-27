package com.example.order.client;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.stereotype.Component;

@Component
public class InstanceLoggingLoadBalancerLifecycle
implements LoadBalancerLifecycle<RequestDataContext, ResponseData, ServiceInstance> {
    @Override
    public void onStart(Request<RequestDataContext> request) {
        // 可选
    }

    @Override
    public void onStartRequest(Request<RequestDataContext> request, Response<ServiceInstance> lbResponse) {

        ServiceInstance instance = lbResponse.getServer();
        if (instance != null) {
            System.out.println("[LB选中实例] " + instance.getHost() + ":" + instance.getPort());
        }
    }

    @Override
    public void onComplete(CompletionContext<ResponseData, ServiceInstance, RequestDataContext> completionContext) {
        // 可选
    }
}
