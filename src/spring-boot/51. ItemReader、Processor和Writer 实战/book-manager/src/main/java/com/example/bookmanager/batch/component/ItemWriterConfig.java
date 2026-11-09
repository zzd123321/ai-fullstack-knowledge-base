package com.example.bookmanager.batch.component;

import com.example.bookmanager.model.Book;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class ItemWriterConfig {

    @Bean
    public JdbcBatchItemWriter<Book> bookWriterWithSetter(DataSource dataSource) {

        return new JdbcBatchItemWriterBuilder<Book>()
                .dataSource(dataSource)
                .sql("INSERT INTO book (title, author, price, stock) VALUES (?, ?, ?, ?)")
                .itemPreparedStatementSetter((book, ps) -> {
                    ps.setString(1, book.getTitle());
                    ps.setString(2, book.getAuthor());
                    ps.setDouble(3, book.getPrice());
                    ps.setInt(4, book.getStock());
                })
                .build();

    }

    @Bean
    public JdbcBatchItemWriter<Book> bookWriterWithNamedParams(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Book>()
                .dataSource(dataSource)
                .sql("INSERT INTO book (title, author, price, stock) " +
                        "VALUES (:title, :author, :price, :stock)")
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .build();
    }
}
