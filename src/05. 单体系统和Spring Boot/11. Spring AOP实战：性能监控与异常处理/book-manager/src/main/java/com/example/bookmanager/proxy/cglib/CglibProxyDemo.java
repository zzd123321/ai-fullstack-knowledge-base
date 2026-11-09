package com.example.bookmanager.proxy.cglib;

import com.example.bookmanager.model.Book;
import com.example.bookmanager.service.impl.BookServiceImpl;
import org.springframework.cglib.proxy.Enhancer;

import java.util.List;

public class CglibProxyDemo {
    public static void main(String[] args) {

        // 创建Enhancer
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(BookServiceImpl.class); // 设置父类
        enhancer.setCallback(new LoggingMethodInterceptor()); // 设置回调，嵌入逻辑

        BookServiceImpl proxy = (BookServiceImpl)enhancer.create();

        // 通过代理调用方法（增强后的行为）
        Book book = new Book();
        book.setTitle("Title");
        book.setAuthor("Author");
        book.setPrice(100.0);
        book.setStock(100);
        proxy.createBook(book);

        List<Book> books = proxy.getAllBooks();
        System.out.println("最终结果：" + books);
    }
}
