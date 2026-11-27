package com.example.order.service.vo;

import com.example.order.model.Order;
import lombok.Data;

import java.util.List;

@Data
public class OrderDetailVO {

    /** 订单主表信息 */
    private OrderVO order;

    /** 订单项列表 */
    private List<OrderItemVO> items;

    @Data
    public static class OrderItemVO {
        private Long itemId;      // 商品快照ID
        private Long productId;   // 原商品ID
        private String title;     // 商品标题（快照）
        private String author;    // 作者（快照）
        private Double price;     // 下单时单价
        private Integer quantity; // 数量
        private Double subtotal;  // 小计 = price * quantity
    }
}