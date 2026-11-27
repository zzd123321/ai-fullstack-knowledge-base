package com.example.order.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderItemRequest {

    @NotNull(message = "商品ID不能为空")
    private Long productId;

    @NotNull(message = "数量不能为空")
    @Min(value = 1, message = "数量至少为1")
    private Integer quantity;

    // 由前端传入（模拟商品信息，引入 OpenFeign 后改为通过商品服务查询）
    @NotNull(message = "商品标题不能为空")
    private String title;

    @NotNull(message = "商品作者不能为空")
    private String author;

    @NotNull(message = "商品价格不能为空")
    @Min(value = 0, message = "价格不能为负数")
    private Double price;
}