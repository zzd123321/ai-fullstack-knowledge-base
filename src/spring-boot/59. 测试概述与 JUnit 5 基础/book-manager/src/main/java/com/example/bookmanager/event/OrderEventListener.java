package com.example.bookmanager.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListener {

    @EventListener
    public OrderNotificationEvent handleOrderCreated(OrderCreatedEvent event) {

        System.out.println("发送订单确认邮件：订单号=" + event.getOrderNumber());

        return new OrderNotificationEvent(this, event.getOrderId(), event.getOrderNumber());
    }

    @EventListener
    public void handleOrderNotified(OrderNotificationEvent event) {

        System.out.println("收到订单通知：" + event.getOrderNumber());
    }

}
