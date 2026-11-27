package com.example.order.message;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQMessageListener(
        topic = "order-topic",
        selectorExpression = "created",
        consumerGroup = "book-order-consumer-group"
)
public class OrderCreatedConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {

        log.info("[RocketMQ消费者] 收到消息：{}", message);
        // 添加任何业务操作：比方说扣减库存、发放积分
    }
}
