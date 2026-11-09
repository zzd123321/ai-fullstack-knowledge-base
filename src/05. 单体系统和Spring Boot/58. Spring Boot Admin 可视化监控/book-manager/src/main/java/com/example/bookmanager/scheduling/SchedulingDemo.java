package com.example.bookmanager.scheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulingDemo {
    private static final Logger logger = LoggerFactory.getLogger(StockCheckService.class);

    @Scheduled(fixedRate = 5000)
    @Async("taskExecutor")
    public void fixedRateDemo() throws InterruptedException {
        logger.info("[fixedRate] 开始，线程：{}",  Thread.currentThread().getName());
        Thread.sleep(7000); // 模拟任务重叠的场景
        logger.info("[fixedRate] 结束");
    }

    @Scheduled(fixedDelay = 5000)
    public void fixedDelayDemo() throws InterruptedException {
        logger.info("[fixedDelay] 开始，线程：{}",  Thread.currentThread().getName());
        Thread.sleep(7000); // 模拟任务重叠的场景
        logger.info("[fixedDelay] 结束");
    }
}
