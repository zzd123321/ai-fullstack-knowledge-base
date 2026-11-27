package com.example.order.message;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("mq_consume_record")
public class MqConsumeRecord {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String consumerGroup;
    private String bizKey;
    private String topic;
    private String status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}