package com.example.bookmanager.proxy.jdk;

import com.example.bookmanager.model.Book;
import com.example.bookmanager.repository.BookRepository;
import com.example.bookmanager.service.BookService;
import com.example.bookmanager.service.impl.BookServiceImpl;

import java.lang.reflect.Proxy;
import java.util.List;

public class JdkProxyDemo {
    public static void main(String[] args) {

        // 创建目标对象
        BookService target = new BookServiceImpl(new BookRepository());

        // 创建代理对象
        BookService proxy = (BookService) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new LoggingInvocationHandler(target)
        );

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
