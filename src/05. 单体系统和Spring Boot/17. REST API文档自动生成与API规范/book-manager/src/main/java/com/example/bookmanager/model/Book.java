package com.example.bookmanager.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Schema(description = "图书实体")
public class Book {

    @Schema(description = "图书ID", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "书名不能为空")
    @Size(min = 1, max = 100, message = "书名长度必须在1-100之间")
    @Schema(description = "图书名称", required = true, example = "Spring Boot 实战")
    private String title;

    @NotBlank(message = "作者不能为空")
    @Size(max = 50, message = "作者名不能超过50个字符")
    @Schema(description = "作者姓名", required = true, example = "Craig Walls")
    private String author;

    @NotNull(message = "价格不能为空")
    @DecimalMin(value = "0.00", message = "价格不能为负数")
    @DecimalMax(value = "9999.99", message = "价格不能超过9999.99")
    @Schema(description = "图书价格", required = true, example = "79.00")
    private Double price;

    @NotNull(message = "库存不能为空")
    @Min(value = 0, message = "库存不能为负数")
    @Schema(description = "库存数量", required = true, example = "10")
    private Integer stock;

    // 可根据需要添加额外字段，此处保持与原附件一致
}