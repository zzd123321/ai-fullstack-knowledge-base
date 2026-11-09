package com.example.bookmanager.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncLogService {

    private static final Logger logger = LoggerFactory.getLogger(AsyncLogService.class);

    @Autowired
    @Lazy
    private AsyncLogService self;

    public void logOperationSync(String message) {
        logger.info("[同步日志] 当前线程：{}， 消息：{}", Thread.currentThread().getName(), message);

        // 模拟耗时操作（如写入文件或数据库）
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        logger.info("[同步日志] 日志记录完成：{}", message);
    }

    @Async("taskExecutor")
    public void logOperation(String message) {
        logger.info("[异步日志] 当前线程：{}， 消息：{}", Thread.currentThread().getName(), message);

        // 模拟耗时操作（如写入文件或数据库）
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        logger.info("[异步日志] 日志记录完成：{}", message);
    }

    public void logWrapper(String msg) {
        logger.info("当前线程：{}", Thread.currentThread().getName());

        // 自调用，不会异步执行
//        this.logOperation(msg);

        // 通过代理调用，异步执行
        self.logOperation(msg);
    }

    @Async("coreExecutor")
    public void logTask(String msg) {
        // 使用核心线程池
        System.out.println("核心任务: " + Thread.currentThread().getName());
    }

    @Async("reportExecutor")
    public void reportTask() {
        // 使用报表线程池
        System.out.println("报表任务: " + Thread.currentThread().getName());
    }

    @Async("mailExecutor")
    public void mailTask(String email) {
        // 使用邮件线程池
        System.out.println("邮件任务: " + Thread.currentThread().getName());
    }
}
