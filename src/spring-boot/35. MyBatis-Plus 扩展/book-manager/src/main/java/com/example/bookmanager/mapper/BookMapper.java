package com.example.bookmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bookmanager.model.Book;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BookMapper extends BaseMapper<Book> {
    // 无需编写任何方法，BaseMapper 已提供完整 CRUD
}