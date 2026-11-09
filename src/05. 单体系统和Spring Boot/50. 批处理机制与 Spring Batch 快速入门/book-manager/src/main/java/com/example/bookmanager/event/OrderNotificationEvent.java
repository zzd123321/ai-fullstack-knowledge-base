package com.example.bookmanager.event;

import org.springframework.context.ApplicationEvent;

public class OrderNotificationEvent extends ApplicationEvent {

    private final Long orderId;
    private final String orderNumber;

    public OrderNotificationEvent(Object source, Long orderId, String orderNumber) {
        super(source);
        this.orderId = orderId;
        this.orderNumber = orderNumber;
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }
}
