package com.example.product.controller;

import com.example.common.model.Result;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/config")
@RequiredArgsConstructor
public class ConfigUpdateController {

    private final RocketMQTemplate rocketMQTemplate;

    @GetMapping("/refresh-category")
    public Result<String> refreshCategory() {
        rocketMQTemplate.syncSend("config-update-topic", "分类数据已更新");
        return Result.success("刷新通知已发送");
    }
}