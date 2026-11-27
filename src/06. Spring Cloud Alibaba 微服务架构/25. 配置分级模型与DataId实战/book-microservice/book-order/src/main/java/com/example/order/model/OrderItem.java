package com.example.order.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("order_item")
public class OrderItem {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long orderId;
    private Long itemId;
    private Integer quantity;
    private Double price;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}