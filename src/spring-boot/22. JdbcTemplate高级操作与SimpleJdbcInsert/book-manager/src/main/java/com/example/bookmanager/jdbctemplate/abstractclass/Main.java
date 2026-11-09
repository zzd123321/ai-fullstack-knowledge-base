package com.example.bookmanager.jdbctemplate.abstractclass;


import com.example.bookmanager.model.Book;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        AbstractJdbcTemplate template = new BookJdbcTemplate();

        List<Book> bookList = (List<Book>)template.execute("SELECT * FROM BOOK");

        for (Book book : bookList) {
            System.out.println(book);
        }
    }
}
