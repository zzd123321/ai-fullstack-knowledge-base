package com.example.bookmanager.model;


import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class Book {
    private Long id;

    @NotBlank(message = "书名不能为空")
    @Size(min = 1, max = 100, message = "书名长度必须在1-100之间")
    private String title;

    @NotBlank(message = "作者不能为空")
    @Size(max = 50, message = "作者名不能超过50个字符")
    private String author;

    @NotNull(message = "价格不能为空")
    @DecimalMin(value = "0.00", message = "价格不能为负数")
    @DecimalMax(value = "9999.99", message = "价格不能超过9999.99")
    private Double price;

    @NotNull(message = "库存不能为空")
    @Min(value = 0, message = "库存不能为负数")
    private Integer stock;

}