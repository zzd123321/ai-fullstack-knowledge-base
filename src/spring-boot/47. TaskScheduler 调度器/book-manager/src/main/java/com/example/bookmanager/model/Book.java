package com.example.bookmanager.model;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@TableName("book")
public class Book {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String title;
    private String author;
    private Double price;
    private Integer stock;
    private Integer sales;

    @TableField("category")
    private String category;

    @TableField("isbn")
    private String isbn;

    @TableField("description")
    private String description;

    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    /**
     * 逻辑删除字段（需在数据库中添加 deleted 列）
     */
    @TableLogic
    @TableField("deleted")
    private Integer deleted;
}