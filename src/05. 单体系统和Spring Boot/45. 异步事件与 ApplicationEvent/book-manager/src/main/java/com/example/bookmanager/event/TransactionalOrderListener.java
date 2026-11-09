package com.example.bookmanager.event;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class TransactionalOrderListener {

    // 事务提交后触发，用于更新商品销量
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleAfterCommit(OrderCreatedEvent orderCreatedEvent) {

        System.out.println("事务已提交，更新商品销量，订单Id=" + orderCreatedEvent.getOrderId());

        orderCreatedEvent.getItems().forEach(item -> {
            System.out.println("图书Id=" + item.getBookId());

            // TODO：模拟更新商品的销量
            // salesService.increaseSales(item.getBookId(), item.getQuantity());
        });

    }


    // 事务回滚后触发，用于发送告警
    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void handleAfterRollback(OrderCreatedEvent orderCreatedEvent) {
        System.out.println("事务已回滚，订单创建失败，订单Id=" + orderCreatedEvent.getOrderId());

        // TODO:触发各种告警机制
    }
}
