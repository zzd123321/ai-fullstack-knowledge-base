package com.example.bookmanager.scheduling.cron;

import com.example.bookmanager.scheduling.StockCheckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CronDemo {
    private static final Logger logger = LoggerFactory.getLogger(StockCheckService.class);

    // 每天凌晨2点执行的任务
    @Scheduled(cron = "0 0 2 * * ?")
    public void dailyTask() {
        logger.info("每天凌晨2点执行：{}", LocalDateTime.now());
    }

    @Scheduled(cron = "0/10 * * * * ?")
    public void verify() {
        logger.info("Cron 任务执行，当前时间: {}", LocalDateTime.now());
    }
}
