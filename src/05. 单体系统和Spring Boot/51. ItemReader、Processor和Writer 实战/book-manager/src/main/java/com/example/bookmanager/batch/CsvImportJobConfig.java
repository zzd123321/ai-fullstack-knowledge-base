package com.example.bookmanager.batch;

import com.example.bookmanager.batch.component.BookFieldSetMapper;
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

            // 2. 过滤无效数据（返回null表示过滤）
            if(book.getTitle() == null || book.getTitle().isBlank()) {
                System.out.println("过滤：书名为空");
                return null;
            }
            if(book.getAuthor() == null || book.getAuthor().isBlank()) {
                System.out.println("过滤：作者为空" + book.getAuthor());
                return null;
            }

            if(book.getPrice() == null || book.getPrice() < 0) {
                System.out.println("过滤：价格为负：" + book.getPrice());
                return null;
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
                               ItemWriter<Book> csvBookWriter) {

        return new StepBuilder("csvImportStep", jobRepository)
                .<Book, Book>chunk(3, transactionManager) // Chunk大小为3
                .reader(csvBookReader)
                .processor(csvBookProcessor)
                .writer(csvBookWriter)
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
