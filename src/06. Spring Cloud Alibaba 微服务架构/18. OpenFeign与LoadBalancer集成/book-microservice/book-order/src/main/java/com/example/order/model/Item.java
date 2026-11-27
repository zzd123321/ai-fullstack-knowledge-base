package com.example.order.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("item")
public class Item {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long productId;
    private String title;
    private String author;
    private Double price;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}