package com.example.bookmanager.batch;

import com.example.bookmanager.model.Book;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Configuration
public class FirstBatchJobConfig {

    // ItemReader: 从内存列表中读取图书数据

    @Bean
    public ItemReader<Book> bookReader() {
        List<Book> books = Arrays.asList(
                new Book("Spring Boot 实战", "Craig Walls", 79.00, 10,
                        "后端开发", "978-7-115-56161-9",
                        "Spring Boot 从入门到实战，覆盖自动配置、Starter、Actuator 等核心特性"),
                new Book("Java 核心技术", "Cay S. Horstmann", 89.00, 8,
                        "编程语言", "978-7-111-54711-6",
                        "Java 领域经典教材，涵盖基础语法、集合、并发与函数式编程"),
                new Book("深入理解 Java 虚拟机", "周志明", 129.00, 5,
                        "底层原理", "978-7-111-64123-1",
                        "JVM 内存模型、垃圾回收、类加载机制与性能调优的权威解读"),
                new Book("Effective Java", "Joshua Bloch", 99.00, 6,
                        "编程实践", "978-7-111-61257-6",
                        "Java 编程最佳实践，90 条改善代码质量的经验法则"),
                new Book("Spring 源码深度解析", "郝佳", 99.00, 3,
                        "源码剖析", "978-7-115-48598-7",
                        "从源码层面剖析 Spring IoC、AOP、事务等核心模块实现")
        );

        return new ListItemReader<>(books);
    }

    // ItemProcessor：处理图书数据（打印日志）
    @Bean
    public ItemProcessor<Book, Book> bookProcessor() {
        return book -> {
            System.out.println("处理图书：" + book.getTitle());

            // 可以在这里添加数据转化、校验等各种业务逻辑
            return book;
        };
    }

    // ItemWriter: 批量写入数据库
    @Bean
    public ItemWriter<Book> bookWriter(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return items -> {
            for (Book book : items) {
                jdbcTemplate.update(
                        "INSERT INTO book (title, author, price, stock, sales, category, isbn, description, deleted, created_at, updated_at) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                        book.getTitle(),
                        book.getAuthor(),
                        book.getPrice(),
                        book.getStock(),
                        book.getSales(),
                        book.getCategory(),
                        book.getIsbn(),
                        book.getDescription(),
                        book.getDeleted(),
                        Timestamp.valueOf(LocalDateTime.now()),   // created_at
                        Timestamp.valueOf(LocalDateTime.now())    // updated_at
                );
            }

            System.out.println("写入：" + items.size() + "条图书数据");
        };
    }

    // 定义Step
    @Bean
    public Step importBookStep(JobRepository jobRepository,
                               PlatformTransactionManager transactionManager,
                               ItemReader<Book> bookReader,
                               ItemProcessor<Book, Book> bookProcessor,
                               ItemWriter<Book> bookWriter) {

        return new StepBuilder("importBookStep", jobRepository)
                .<Book, Book>chunk(2, transactionManager) // Chunk大小为2
                .reader(bookReader)
                .processor(bookProcessor)
                .writer(bookWriter)
                .build();
    }

    // 定义Job
    @Bean
    public Job importBookJob(JobRepository jobRepository, Step importBookStep) {
        return new JobBuilder("importBookJob", jobRepository)
                .start(importBookStep)
                .build();
    }

}
