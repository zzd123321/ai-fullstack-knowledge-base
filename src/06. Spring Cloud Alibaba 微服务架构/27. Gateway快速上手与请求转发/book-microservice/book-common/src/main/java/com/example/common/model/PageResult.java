package com.example.common.model;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageResult<T> implements Serializable {

    private Integer page;
    private Integer size;
    private Long total;
    private Integer totalPage;
    private List<T> data;

    public PageResult(Integer page, Integer size, Long total, List<T> data) {
        this.page = page;
        this.size = size;
        this.total = total;
        this.totalPage = size > 0 ? (int)Math.ceil((double) total / size) : 0;
        this.data = data;
    }

    public static <T> PageResult<T> of(IPage<T> page) {

        return new PageResult<>(
                (int)page.getCurrent(),
                (int)page.getSize(),
                page.getTotal(),
                page.getRecords()
        );
    }
}
