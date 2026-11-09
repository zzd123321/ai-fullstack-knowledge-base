package com.example.bookmanager.event;

import com.example.bookmanager.model.Order;
import com.example.bookmanager.model.OrderItem;
import lombok.Data;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;


public class OrderCreatedEvent extends ApplicationEvent {

    private final Long orderId;
    private final String orderNumber;
    private final Double totalAmount;
    private final String status;
    private final LocalDateTime createdAt;
    private final List<OrderItem> items;

    public OrderCreatedEvent(Object source, Order order) {
        super(source);
        this.orderId = order.getId();
        this.orderNumber = order.getOrderNumber();
        this.totalAmount = order.getTotalAmount();
        this.status = order.getStatus();
        this.createdAt = order.getCreatedAt();
        this.items = order.getItems() == null
                ? Collections.emptyList()
                : List.copyOf(order.getItems());
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public int getTotalQuantity() {
        return items.stream()
                .mapToInt(OrderItem::getQuantity)
                .sum();
    }

    @Override
    public String toString() {
        return "OrderCreatedEvent{" +
                "orderId=" + orderId +
                ", orderNumber='" + orderNumber + '\'' +
                ", totalAmount=" + totalAmount +
                ", totalQuantity=" + getTotalQuantity() +
                '}';
    }
}
