package com.example.bookmanager.jdbctemplate.callback;

import com.example.bookmanager.model.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        String sql = "SELECT * FROM BOOK";
        CallbackJdbcTemplate template = new CallbackJdbcTemplate();

//        Object object = template.execute(new StatementCallback() {
//            @Override
//            public Object handleStatement(Statement stmt) throws SQLException {
//                ResultSet rs = stmt.executeQuery(sql);
//                List<Book> books = new ArrayList<Book>();
//                while (rs.next()) {
//                    Book book = new Book();
//                    book.setId(rs.getLong("id"));
//                    book.setTitle(rs.getString("title"));
//                    book.setAuthor(rs.getString("author"));;
//                    book.setPrice(rs.getDouble("price"));
//                    book.setStock(rs.getInt("stock"));
//                    books.add(book);
//                }
//
//                return books;
//            }
//        });

        Object object = template.execute(stmt -> {
            ResultSet rs = stmt.executeQuery(sql);
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
        });


        List<Book> books = (List<Book>) object;
        for (Book book : books) {
            System.out.println(book);
        }
    }
}
