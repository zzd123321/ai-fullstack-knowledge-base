package com.example.bookmanager.mapper;

import com.example.bookmanager.model.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BookMapper {

    /**
     * 根据 ID 查询图书
     */
    Book selectById(@Param("id") Long id);

    /**
     * 查询所有图书
     */
    List<Book> selectAll();

    /**
     * 新增图书（返回插入行数）
     */
    int insert(Book book);

    /**
     * 更新图书（返回更新行数）
     */
    int update(Book book);

    /**
     * 根据 ID 删除图书（返回删除行数）
     */
    int deleteById(@Param("id") Long id);
}