package com.example.order.message;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.order.mapper.MqConsumeRecordMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(
        topic = "order-topic",
        selectorExpression = "created",
        consumerGroup = "book-order-consumer-group"
)
public class OrderCreatedConsumer implements RocketMQListener<String> {

    private static final String CONSUMER_GROUP = "book-order-consumer-group";
    private static final String TOPIC = "order-topic";

    private final MqConsumeRecordMapper consumeRecordMapper;

    @Override
    public void onMessage(String message) {

        Long orderId = extractOrderId(message);
        if(orderId == null) {
            log.warn("[消费者] 消息格式异常，无法提取OrderId: {}", message);
            return;
        }

        String bizKey = String.valueOf(orderId);

        // 1. 幂等检查：消费记录
        LambdaQueryWrapper<MqConsumeRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MqConsumeRecord::getConsumerGroup, CONSUMER_GROUP)
                        .eq(MqConsumeRecord::getBizKey, bizKey);

        MqConsumeRecord existing = consumeRecordMapper.selectOne(queryWrapper);

        if(existing != null && "SUCCESS".equals(existing.getStatus())) {
            log.info("[消费者] 消息已处理过，跳过：orderId={}", orderId);
            return;
        }

        // 2. 首次处理，插入消费记录（唯一性的保障）
        if(existing == null) {
            MqConsumeRecord mqConsumeRecord = new MqConsumeRecord();
            mqConsumeRecord.setConsumerGroup(CONSUMER_GROUP);
            mqConsumeRecord.setBizKey(bizKey);
            mqConsumeRecord.setTopic(TOPIC);
            mqConsumeRecord.setStatus("PROCESSING");

            try {
                consumeRecordMapper.insert(mqConsumeRecord);
            } catch (DuplicateKeyException e) {
                // 并发场景：另一个实例已插入，说明正在处理或者已处理
                log.info("[消费者] 并发重复消息，跳过：orderId={}", orderId);
                return;
            }
        }

        // 3. 执行业务逻辑
        try {
            // TODO：执行具体业务逻辑
            updateStatus(CONSUMER_GROUP, bizKey, "SUCCESS");
            log.info("[消费者] 订单创建消息处理成功：orderId={}", orderId);
        } catch (Exception e) {
            // 处理失败，更新状态
            updateStatus(CONSUMER_GROUP, bizKey, "FAILED");
            log.error("[消费者] 订单创建消息处理失败：orderId={}", orderId);
            throw new RuntimeException("消费失败", e);
        }

    }

    private void updateStatus(String group, String bizKey, String status) {
        LambdaQueryWrapper<MqConsumeRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MqConsumeRecord::getConsumerGroup, group)
                .eq(MqConsumeRecord::getBizKey, bizKey);
        MqConsumeRecord update = new MqConsumeRecord();
        update.setStatus(status);
        consumeRecordMapper.update(update, queryWrapper);
    }

    private Long extractOrderId(String message) {
        try {
            return Long.parseLong(message.replaceAll(".*orderId=(\\d+).*", "$1"));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
