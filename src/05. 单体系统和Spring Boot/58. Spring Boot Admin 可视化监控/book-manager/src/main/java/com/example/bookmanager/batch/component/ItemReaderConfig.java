package com.example.bookmanager.batch.component;

import com.example.bookmanager.model.Book;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ItemReaderConfig {

    @Bean
    public FlatFileItemReader<Book> csvReader() {
        // 通过Builder创建目标组件
        return new FlatFileItemReaderBuilder<Book>()
                .name("csvReader")
                .resource(new ClassPathResource("data/book.csv"))
                .linesToSkip(1) // 跳过表头
                .delimited()
                .delimiter(",")
                .names("title", "author", "price", "stock") //列名
//                .targetType(Book.class) // 直接映射到Book对象
                .fieldSetMapper(new BookFieldSetMapper())
                .build();
    }

    @Bean
    public JdbcPagingItemReader<Book> dbBookReader(DataSource dataSource) {
        Map<String, Order> sortKeys = new HashMap<String, Order>();
        sortKeys.put("id", Order.ASCENDING);

        return new JdbcPagingItemReaderBuilder<Book>()
                .name("dbBookReader")
                .dataSource(dataSource)
                .selectClause("SELECT it, title, author, price,stock")
                .fromClause("FROM book")
                .sortKeys(sortKeys)
                .rowMapper(new BeanPropertyRowMapper<Book>(Book.class))
                .pageSize(10)
                .build();
    }
}
