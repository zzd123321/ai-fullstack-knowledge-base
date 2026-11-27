package com.example.order.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderMessageProducer {

    private final RocketMQTemplate rocketMQTemplate;

    private static final String TOPIC_TAG = "order-topic:created";

    // 同步发送：可靠
    public void sendOrderCreatedSync(Long orderId) {
        String message = "订单已创建，orderId=" + orderId;
        SendResult result = rocketMQTemplate.syncSend(TOPIC_TAG, message);
        if(result.getSendStatus() != SendStatus.SEND_OK) {
            log.error("[同步发送] 失败：orderId={}, status={}", orderId, result.getSendStatus());
            throw new RuntimeException("订单消息发送失败：" + result.getSendStatus());
        }

        log.info("[同步发送] 成功: orderId={}, msgId={}", orderId, result.getMsgId());
    }

    // 异步发送：回调处理
    public void sendOrderCreatedAsync(Long orderId) {
        String message = "订单已创建，orderId=" + orderId;

        rocketMQTemplate.asyncSend(TOPIC_TAG, message, new SendCallback() {

            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("[异步发送] 成功: orderId={}, msgId={}", orderId, sendResult.getMsgId());
            }

            @Override
            public void onException(Throwable throwable) {
                log.error("[异步发送] 失败，需要补偿：orderId={}", orderId, throwable);
                // 生产环境：写入本地失败表，由定时任务进行补偿发送
            }
        });
    }

    // 单向发送：不可靠
    public void sendAccessLog(Long orderId) {
        String message = "订单已创建，orderId=" + orderId;

        rocketMQTemplate.sendOneWay("log-topic:access", message);
        // 不检查结果
    }
}
