package com.example.bookmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bookmanager.model.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface BookMapper extends BaseMapper<Book> {
    // 无需编写任何方法，BaseMapper 已提供完整 CRUD

    // 自定义方法：仍然可以使用 XML 或注解
    /**
     * 根据作者查询
     */
    @Select("SELECT * FROM book WHERE author = #{author}")
    List<Book> selectByAuthor(@Param("author") String author);

    /**
     * 更新库存
     */
    @Update("UPDATE book SET stock = stock + #{delta} WHERE id = #{id}")
    int updateStock(@Param("id") Long id, @Param("delta") int delta);

    @Select("SELECT COALESCE(SUM(stock), 0) FROM book")
    Long sumStock();

    @Select("SELECT COUNT(1) FROM book")
    Long count();
}