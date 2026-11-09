package com.example.bookmanager.event;

import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class ConditionalOrderListener {

    // 当订单金额 > 1000时通知营销系统
    @Order(1)
    @EventListener(condition = "#event.totalAmount > 1000")
    public void handleLargeOrder(OrderCreatedEvent event) {
        System.out.println("大额订单通知，订单号：" + event.getOrderNumber()
        + ", 金额=" + event.getTotalAmount());
    }

    // 当商品总数量 > 3时记录批量订单日志
    @Order(2)
    @EventListener(condition = "#event.totalQuantity > 3")
    public void handleBulkOrder(OrderCreatedEvent event) {
        System.out.println("批量订单，订单号：" + event.getOrderNumber()
                + ", 总数量=" + event.getTotalQuantity());
    }
}
