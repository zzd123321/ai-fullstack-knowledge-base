package com.example.order.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderMessageProducer {

    private final RocketMQTemplate rocketMQTemplate;

    public void sendOrderCreatedMessage(Long orderId) {
        String message = "订单已创建，orderId=" + orderId;
        rocketMQTemplate.syncSend("order-topic:created", message);
        log.info("[RocketMQ] 消息已发送：{}", message);
    }
}
