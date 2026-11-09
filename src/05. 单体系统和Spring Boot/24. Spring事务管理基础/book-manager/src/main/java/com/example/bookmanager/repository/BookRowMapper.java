package com.example.bookmanager.repository;

import com.example.bookmanager.model.Book;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class BookRowMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();
        book.setId(rs.getLong("id"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        book.setPrice(rs.getDouble("price"));
        book.setStock(rs.getInt("stock"));
        book.setCategory(rs.getString("category"));
        if(rs.getTimestamp("created_at") != null) {
            book.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        }
        if(rs.getTimestamp("updated_at") != null) {
            book.setCreatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        }
        return book;
    }
}
