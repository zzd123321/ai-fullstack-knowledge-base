package com.example.bookmanager.jdbctemplate.abstractclass;


import com.example.bookmanager.model.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookJdbcTemplate extends AbstractJdbcTemplate {
    @Override
    protected Object handleResultSet(ResultSet rs) throws SQLException {
        List<Book> books = new ArrayList<Book>();
        while (rs.next()) {
            Book book = new Book();
            book.setId(rs.getLong("id"));
            book.setTitle(rs.getString("title"));
            book.setAuthor(rs.getString("author"));;
            book.setPrice(rs.getDouble("price"));
            book.setStock(rs.getInt("stock"));
            books.add(book);
        }
        return books;
    }
}
