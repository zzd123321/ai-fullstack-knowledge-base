package com.example.bookmanager.batch.component;

import com.example.bookmanager.model.Book;
import lombok.Data;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class ItemProcessorConfig {

    @Bean
    public ItemProcessor<BookCsv, Book> bookConverter() {
        return csv ->  {
            Book book = new Book();
            book.setTitle(csv.getTitle().trim());
            book.setAuthor(csv.getAuthor().trim());
            book.setPrice(csv.getPrice());
            book.setStock(csv.getStock());

            return book;
        };
    }

    @Bean
    public ItemProcessor<Book, Book> validBookFilter() {
        return book -> {
            // 过滤掉价格小于0的图书
            if(book.getPrice() == null || book.getPrice() < 0) {
                System.out.println("过滤无效图书：" + book.getTitle());
                return null;
            }

            return book;
        };
    }

    @Bean
    public ItemProcessor<Book, Book> bookValidator() {
        return book -> {
            if(book.getTitle() == null || book.getTitle().isBlank()) {
                throw new IllegalArgumentException("书名不能为空");
            }

            if(book.getStock() == null || book.getStock() < 0) {
                throw new IllegalArgumentException("库存不能为负数或者为空");
            }
            return book;
        };
    }

    @Bean
    public ItemProcessor<Book, Book> compositeProcessor(
            ItemProcessor<Book, Book> validBookFilter,
            ItemProcessor<Book, Book> bookValidator
    ) {
        CompositeItemProcessor<Book, Book> compositeProcessor = new CompositeItemProcessor<>();
        compositeProcessor.setDelegates(Arrays.asList(validBookFilter, bookValidator));
        return compositeProcessor;
    }

    @Data
    private class BookCsv {
        private String title;
        private String author;
        private Double price;
        private Integer stock;
    }
}
