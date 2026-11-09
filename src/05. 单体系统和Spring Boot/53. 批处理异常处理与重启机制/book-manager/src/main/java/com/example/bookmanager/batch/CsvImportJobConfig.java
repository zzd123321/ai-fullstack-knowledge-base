package com.example.bookmanager.batch;

import com.example.bookmanager.batch.component.BookFieldSetMapper;
import com.example.bookmanager.batch.faulttolerant.BookRetryListener;
import com.example.bookmanager.batch.faulttolerant.BookSkipListener;
import com.example.bookmanager.batch.faulttolerant.BookSkipPolicy;
import com.example.bookmanager.model.Book;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class CsvImportJobConfig {

    // ItemReader: 从CSV文件进行读取图书数据
    @Bean
    public FlatFileItemReader<Book> csvBookReader() {
        // 通过Builder创建目标组件
        return new FlatFileItemReaderBuilder<Book>()
                .name("csvBookReader")
                .resource(new ClassPathResource("data/book.csv"))
                .linesToSkip(1) // 跳过表头
                .delimited()
                .delimiter(",")
                .names("title", "author", "price", "stock") //列名
                .targetType(Book.class) // 直接映射到Book对象
                .encoding("UTF-8")
                // 启用状态保存
                .saveState(true)
                .build();
    }

    // ItemProcessor：数据校验 + 数据清洗
    @Bean
    public ItemProcessor<Book, Book> csvBookProcessor() {
        return book -> {
            // 1. 清洗数据（去掉首尾空格）
            if(book.getTitle() != null) {
                book.setTitle(book.getTitle().trim());
            }
            if(book.getAuthor() != null) {
                book.setTitle(book.getAuthor().trim());
            }

            // 2. 数据校验（抛出异常，由Skip处理）
            if(book.getTitle() == null || book.getTitle().isBlank()) {
                throw new IllegalArgumentException("书名为空");
            }
            if(book.getAuthor() == null || book.getAuthor().isBlank()) {
                throw new IllegalArgumentException("作者为空");
            }

            if(book.getPrice() == null || book.getPrice() < 0) {
                throw new IllegalArgumentException("价格为负");
            }

            if (book.getStock() == null || book.getStock() < 0) {
                throw new IllegalArgumentException("库存为负数: " + book.getStock());
            }

            // 3. 处理数据（模拟打印日志）
            System.out.println("处理图书：" + book);
            return book;
        };
    }

    // 3. ItemWriter：批量写入数据库
    @Bean
    public JdbcBatchItemWriter<Book> csvBookWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Book>()
                .dataSource(dataSource)
                .sql("INSERT INTO book (title, author, price, stock) " +
                        "VALUES (:title, :author, :price, :stock)")
//                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .beanMapped()
                .build();
    }

    // 定义Step
    @Bean
    public Step csvImportStep(JobRepository jobRepository,
                              PlatformTransactionManager transactionManager,
                              ItemReader<Book> csvBookReader,
                              ItemProcessor<Book, Book> csvBookProcessor,
                              ItemWriter<Book> csvBookWriter,
                              BookSkipListener bookSkipListener) {

        // 指数退避
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(1000); // 初始1秒
        backOffPolicy.setMultiplier(2); // 指定每次乘以2
        backOffPolicy.setMaxInterval(10000); // 最大到10秒

        return new StepBuilder("csvImportStep", jobRepository)
                .<Book, Book>chunk(3, transactionManager) // Chunk大小为3
                .reader(csvBookReader)
                .processor(csvBookProcessor)
                .writer(csvBookWriter)

                // 启用容错机制
                .faultTolerant()
                //使用自定义的SkipPolicy
                .skipPolicy(new BookSkipPolicy())
                // 注册SkipListener
                .listener(bookSkipListener)

                // 最多重试3次
                .retryLimit(3)
                // 重试指定异常
                .retry(TransientDataAccessException.class)
                // 指定退避策略
                .backOffPolicy(backOffPolicy)
                // 注册监听器
                .listener(new BookRetryListener())

                // 允许重启次数（最多3次）
                .startLimit(3)
                // 如果以完成，是否允许再次重启
                .allowStartIfComplete(false)

                .build();
    }

    // 定义Job
    @Bean
    public Job csvImportJob(JobRepository jobRepository, Step csvImportStep) {
        return new JobBuilder("csvImportJob", jobRepository)
                .start(csvImportStep)
                .build();
    }
}
