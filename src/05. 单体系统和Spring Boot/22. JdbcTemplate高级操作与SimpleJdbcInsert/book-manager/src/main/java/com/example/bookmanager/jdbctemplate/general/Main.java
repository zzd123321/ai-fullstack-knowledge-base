package com.example.bookmanager.jdbctemplate.general;

import com.example.bookmanager.model.Book;
import com.example.bookmanager.repository.BookRowMapper;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        // 定义RowMapper
        RowMapper<Book> bookRowMapper = (rs, rowNum) -> {
            Book book = new Book();
            book.setId(rs.getLong("id"));
            book.setTitle(rs.getString("title"));
            book.setAuthor(rs.getString("author"));;
            book.setPrice(rs.getDouble("price"));
            book.setStock(rs.getInt("stock"));
            return book;
        };

        // 创建通用的JdbcTemplate，然后执行通用方法
        // 多条查询
        GeneralJdbcTemplate template = new GeneralJdbcTemplate();
        List<Book> books = template.query("SELECT * FROM BOOK", bookRowMapper);
        System.out.println(books);

        // 单条查询
        Book book = template.queryForObject("SELECT * FROM BOOK WHERE id = 1", bookRowMapper );
        System.out.println(book);
    }
}
