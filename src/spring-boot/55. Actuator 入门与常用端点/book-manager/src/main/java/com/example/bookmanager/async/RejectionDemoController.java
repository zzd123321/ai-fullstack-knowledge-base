package com.example.bookmanager.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadPoolExecutor;

@RestController
@RequestMapping("/api/demo")
public class RejectionDemoController {

    @Autowired
    @Qualifier("customExecutor")
    private ThreadPoolTaskExecutor executor;

    @GetMapping("/reject")
    public String rejectDemo() {
        // 模拟大量提交，触发拒绝策略
        for (int i = 0; i < 1000; i++) {
            final int taskId = i;
            executor.submit(() -> {
                try {
                    Thread.sleep(1000);
                    System.out.println(taskId + "完成");
                } catch (InterruptedException e) {
                }
            });
        }
        return "任务已提交";
    }
}