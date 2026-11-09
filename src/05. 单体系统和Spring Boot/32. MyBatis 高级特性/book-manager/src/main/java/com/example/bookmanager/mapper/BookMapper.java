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

    // 动态查询
    List<Book> searchBooks(@Param("author") String author,
                           @Param("category") String category,
                           @Param("minPrice") Double minPrice,
                           @Param("maxPrice") Double maxPrice,
                           @Param("keyword") String keyword);

//    @Select("SELECT * FROM book")
//    List<Book> selectAll();
//
//    @Select("SELECT * FROM book WHERE id = #{id}")
//    Book selectById(@Param("id") Long id);
//
//    @Insert("INSERT INTO book (title, author, price, stock, sales, category, isbn, description)  VALUES (#{title}, #{author}, #{price}, #{stock}, #{sales}, #{category}, #{isbn}, #{description})")
//    @Options(useGeneratedKeys = true, keyProperty = "id")
//    int insert(Book book);
//
//    @Update("UPDATE book SET title = #{title}, price = #{price} WHERE id = #{id}")
//    int update(Book book);
//
//    @Delete("DELETE FROM book WHERE id = #{id}")
//    int deleteById(@Param("id") Long id);
}