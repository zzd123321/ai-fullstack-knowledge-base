package com.example.bookmanager.scheduling;

import com.example.bookmanager.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class BookScheduledTasks {

    private static final Logger logger = LoggerFactory.getLogger(StockCheckService.class);

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final BookService bookService;
    private final AtomicLong statisticCount = new AtomicLong(0);

    public BookScheduledTasks(BookService bookService) {
        this.bookService = bookService;
    }


    // 缓存预热：应用程序启动后10秒执行一次
    @Scheduled(initialDelay = 10000, fixedRate = Long.MAX_VALUE)
    public void warmUpCache() {
        logger.info("[缓存预热] 开始，时间：{}", LocalDateTime.now().format(FORMATTER));

        // 预热逻辑：加载热门图书到缓存
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        logger.info("[缓存预热] 完成");
    }

    // 图书统计： 每30秒执行一次
    @Scheduled(fixedRate = 30000)
    public void statistic() {
        long count = statisticCount.incrementAndGet();
        logger.info("[销量统计] 第{}次执行， 线程：{}", count, Thread.currentThread().getName());

        long total = bookService.count();
        logger.info("[销量统计] 当前图书总数：{}", total);
    }

    // 缓存清理：上次完成之后再等5分钟执行
    @Scheduled(fixedDelay = 300000)
    public void cleanExpredCache() {
        logger.info("[缓存清理] 开始，时间：{}", LocalDateTime.now().format(FORMATTER));
        // 清理逻辑
        logger.info("[缓存清理] 结束");
    }

    // 库存同步：每10秒执行一次，使用异步方式避免阻塞
    @Scheduled(fixedRate = 10000)
    @Async("taskExecutor")
    public void syncStock() {
        logger.info("[库存同步] 开始，线程：{}", Thread.currentThread().getName());
        // 模拟库存同步逻辑
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        logger.info("[库存同步] 完成");
    }

}
