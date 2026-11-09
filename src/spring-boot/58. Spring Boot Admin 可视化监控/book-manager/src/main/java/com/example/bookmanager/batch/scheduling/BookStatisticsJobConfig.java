package com.example.bookmanager.batch.scheduling;

import com.example.bookmanager.batch.faulttolerant.BookSkipListener;
import com.example.bookmanager.batch.listener.ChunkLoggingListener;
import com.example.bookmanager.batch.listener.ItemLoggingListener;
import com.example.bookmanager.batch.listener.JobLoggingListener;
import com.example.bookmanager.batch.listener.StepLoggingListener;
import com.example.bookmanager.model.BatchBookStatistics;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class BookStatisticsJobConfig {


    private final RowMapper<BatchBookStatistics> batchBookStatisticsRowMapper = (rs, rowNum) -> {
        BatchBookStatistics batchBookStatistics = new BatchBookStatistics();
        batchBookStatistics.setCategory(rs.getString("category"));
        batchBookStatistics.setBookCount(rs.getLong("book_count"));
        batchBookStatistics.setTotalStock(rs.getLong("total_stock"));
        batchBookStatistics.setAvgPrice(rs.getDouble("avg_price"));
        return batchBookStatistics;
    };

    // ItemReader：按分类统计图书
    @Bean
    public JdbcPagingItemReader<BatchBookStatistics> statisticsReader(DataSource dataSource) {

        Map<String, Order> sortKeys = new HashMap<String, Order>();
        sortKeys.put("category", Order.ASCENDING);

        return new JdbcPagingItemReaderBuilder<BatchBookStatistics>()
                .name("statisticsReader")
                .dataSource(dataSource)
                .selectClause("SELECT category, COUNT(*) AS book_count, " +
                        "SUM(stock) AS total_stock, AVG(price) AS avg_price")
                .fromClause("FROM book")
                .groupClause("GROUP BY category")
                .sortKeys(sortKeys)
                .rowMapper(batchBookStatisticsRowMapper)
                .pageSize(10)
                .build();
    }

    // ItemWriter：将统计结果写入统计表
    @Bean
    public ItemWriter<BatchBookStatistics> statisticsWriter(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return items -> {
            for (BatchBookStatistics batchBookStatistics : items) {
                jdbcTemplate.update("INSERT INTO book_statistics " +
                        "(category, book_count, total_stock, avg_price, stat_date) " +
                        "VALUES (?, ?, ?, ?, CURRENT_DATE)",
                        batchBookStatistics.getCategory(),
                        batchBookStatistics.getBookCount(),
                        batchBookStatistics.getTotalStock(),
                        batchBookStatistics.getAvgPrice()
                );
            }
            System.out.println("写入： " + items.size() + "条统计数据");
        };
    }

    // 统计 Step
    @Bean
    public Step statisticsStep(JobRepository jobRepository,
                               PlatformTransactionManager transactionManager,
                               ItemReader<BatchBookStatistics> statisticsReader,
                               ItemWriter<BatchBookStatistics> statisticsWriter,
                               StepLoggingListener stepLoggingListener) {
        return new StepBuilder("statisticsStep", jobRepository)
                .<BatchBookStatistics, BatchBookStatistics>chunk(5, transactionManager)
                .reader(statisticsReader)
                .writer(statisticsWriter)
                .listener(stepLoggingListener)
                .build();
    }


    @Bean
    public Step fullListenerStep(JobRepository jobRepository,
                                 PlatformTransactionManager transactionManager,
                                 ItemReader<BatchBookStatistics> reader,
                                 ItemWriter<BatchBookStatistics> writer,
                                 StepLoggingListener stepLoggingListener,
                                 ChunkLoggingListener chunkLoggingListener,
                                 ItemLoggingListener itemLoggingListener,
                                 BookSkipListener bookSkipListener) {
        return new StepBuilder("fullListenerStep", jobRepository)
                .<BatchBookStatistics, BatchBookStatistics>chunk(3, transactionManager)
                .reader(reader)
                .writer(writer)
                .faultTolerant()
                .skipLimit(5)
                .skip(IllegalArgumentException.class)
                // 注册所有监听器
                .listener(stepLoggingListener)
                .listener(chunkLoggingListener)
                .listener(itemLoggingListener)
                .listener(bookSkipListener)
                .build();
    }


    // 定义Job
    @Bean
    public Job statisticsJob(JobRepository jobRepository,
                             Step statisticsStep,
                             JobLoggingListener jobLoggingListener) {
        return new JobBuilder("fullListenerStep", jobRepository)
                .start(statisticsStep)
                .listener(jobLoggingListener)
                .build();
    }
}
