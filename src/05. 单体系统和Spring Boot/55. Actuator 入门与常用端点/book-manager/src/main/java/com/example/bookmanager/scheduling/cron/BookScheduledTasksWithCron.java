package com.example.bookmanager.scheduling.cron;

import com.example.bookmanager.scheduling.StockCheckService;
import com.example.bookmanager.service.BookService;
import com.example.bookmanager.service.ReportService;
import com.example.bookmanager.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class BookScheduledTasksWithCron {

    private static final Logger logger = LoggerFactory.getLogger(StockCheckService.class);

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private BookService bookService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private StockService stockService;

    private final AtomicLong dailyCount = new AtomicLong(0);
    private final AtomicLong weeklyCount = new AtomicLong(0);
    private final AtomicLong monthlyCount = new AtomicLong(0);

    // 日报生成：每天凌晨2点
    @Scheduled(cron = "0 0 2 * * ?")
    public void generateDailyReport() {
        long count = dailyCount.incrementAndGet();
        logger.info("[日报生成] 第{}次执行，开始时间：{}", count, LocalDateTime.now().format(FORMATTER));

        try {
            long totalBooks = bookService.count();
            reportService.generateDailyReport(totalBooks);
            logger.info("[日报生成] 完成，图书总量：{}", totalBooks);
        } catch (Exception e) {
            logger.error("[日报生成] 失败", e);
        }
    }

    // 周报生成：每周一上午9点
    @Scheduled(cron = "0 0 9 ? * MON")
    public void generateWeeklyReport() {
        long count = weeklyCount.incrementAndGet();
        logger.info("[周报生成] 第 {} 次执行，开始时间: {}", count,
                LocalDateTime.now().format(FORMATTER));

        try {
            reportService.generateWeeklyReport();
            logger.info("[周报生成] 完成");
        } catch (Exception e) {
            logger.error("[周报生成] 失败", e);
        }
    }

    // 月报生成：每月最后一天23点执行
    @Scheduled(cron = "0 0 23 L * ?")
    public void generateMonthlyReport() {
        long count = monthlyCount.incrementAndGet();
        logger.info("[月报生成] 第 {} 次执行，开始时间: {}", count,
                LocalDateTime.now().format(FORMATTER));

        try {
            reportService.generateMonthlyReport();
            logger.info("[月报生成] 完成");
        } catch (Exception e) {
            logger.error("[月报生成] 失败", e);
        }
    }

    @Scheduled(cron = "0 0/30 * * * ?")
    public void cleanCache() {
        logger.info("[缓存清理] 开始，时间: {}", LocalDateTime.now().format(FORMATTER));
        // 清理逻辑
        logger.info("[缓存清理] 完成");
    }

    // 每10分钟同步一次:异步执行
    @Scheduled(cron = "0 0/10 * * * ?")
    @Async("taskExecutor")
    public void syncStock() {
        logger.info("[库存同步] 开始，线程: {}", Thread.currentThread().getName());
        try {
            stockService.syncStock();
            logger.info("[库存同步] 完成");
        } catch (Exception e) {
            logger.error("[库存同步] 失败", e);
        }
    }

    // 数据备份：每天凌晨3点执行
    @Scheduled(cron = "0 0 3 * * ?")
    public void backupDatabase() {
        logger.info("[数据备份] 开始，时间: {}", LocalDateTime.now().format(FORMATTER));
        try {
            Thread.sleep(5000);  // 模拟备份耗时
            logger.info("[数据备份] 完成");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.warn("[数据备份] 被中断");
        }
    }

    // 慢查询：每周日凌晨4点执行
    @Scheduled(cron = "0 0 4 ? * SUN")
    public void cleanSlowQueryLog() {
        logger.info("[慢查询清理] 开始，时间: {}", LocalDateTime.now().format(FORMATTER));
        logger.info("[慢查询清理] 完成");
    }
}
