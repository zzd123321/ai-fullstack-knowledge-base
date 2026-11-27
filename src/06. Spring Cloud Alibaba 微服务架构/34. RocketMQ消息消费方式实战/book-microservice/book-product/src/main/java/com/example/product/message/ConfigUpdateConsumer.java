package com.example.product.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(
        topic = "config-update-topic",
        consumerGroup = "book-product-consumer-group",
        messageModel = MessageModel.CLUSTERING
)
public class ConfigUpdateConsumer implements RocketMQListener<String> {

    private final CategoryCache categoryCache;

    @Override
    public void onMessage(String message) {
        log.info("[配置刷新] 收到分类更新通知：{}", message);
        categoryCache.refresh();
        log.info("[配置刷新] 本地缓存已刷新");
    }
}
