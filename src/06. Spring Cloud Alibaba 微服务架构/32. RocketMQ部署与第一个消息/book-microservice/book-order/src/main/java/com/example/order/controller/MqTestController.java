package com.example.order.controller;

import com.example.common.model.Result;
import com.example.order.message.OrderMessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mq")
@RequiredArgsConstructor
public class MqTestController {

    private final OrderMessageProducer producer;

    @GetMapping("/send/{orderId}")
    public Result<String> sendMessage(@PathVariable(value = "orderId") Long orderId) {
        producer.sendOrderCreatedMessage(orderId);
        return Result.success("消息已发送，orderId=" + orderId);
    }
}