package com.example.bookmanager.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderCreateRequest {

    @NotNull(message = "图书ID不能为空")
    @Min(value = 1, message = "图书ID必须为正数")
    private Long bookId;

    @NotNull(message = "数量不能为空")
    @Min(value = 1, message = "数量必须至少为1")
    private Integer quantity;
}