package com.example.bookmanager.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.example.bookmanager.model.Book;

import java.util.List;

public interface BookService extends IService<Book> {

    // 添加自定义业务方法
    List<Book> searchBooks(String author, String category, Double minPrice, Double maxPrice);


    // 添加根据版本号更新Book的乐观锁方法
    boolean updateBookWithVersion(Book book);
}