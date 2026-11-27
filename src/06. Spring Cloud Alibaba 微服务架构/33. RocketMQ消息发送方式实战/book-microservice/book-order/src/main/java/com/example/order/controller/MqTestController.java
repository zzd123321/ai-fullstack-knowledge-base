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

    @GetMapping("/sync/{orderId}")
    public Result<String> syncSend(@PathVariable("orderId") Long orderId) {
        producer.sendOrderCreatedSync(orderId);
        return Result.success("同步发送成功");
    }

    @GetMapping("/async/{orderId}")
    public Result<String> asyncSend(@PathVariable("orderId") Long orderId) {
        producer.sendOrderCreatedAsync(orderId);
        return Result.success("异步发送已提交");
    }

    @GetMapping("/oneway/{orderId}")
    public Result<String> onewaySend(@PathVariable("orderId") Long orderId) {
        producer.sendAccessLog(orderId);
        return Result.success("单向发送已执行");
    }
}