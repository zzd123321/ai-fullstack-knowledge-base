package com.example.bookmanager.event;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AsyncOrderEventListener {

    @Async("mailExecutor")
    @EventListener
    public void handleOrderCreatedEmail(OrderCreatedEvent event) {
        System.out.println("📧 [异步] 发送邮件，订单号=" + event.getOrderNumber()
                + "，线程: " + Thread.currentThread().getName());

        // 模拟耗时操作
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("📧 [异步] 邮件发送完成");
    }

    @Async("logExecutor")
    @EventListener
    public void handleOrderCreatedLog(OrderCreatedEvent event) {
        System.out.println("📝 [异步] 记录日志，订单ID=" + event.getOrderId()
                + "，线程: " + Thread.currentThread().getName());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("📝 [异步] 日志记录完成");
    }
}
