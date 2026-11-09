package com.example.bookmanager.controller;

import com.example.bookmanager.model.BookStatistics;
import com.example.bookmanager.model.Result;
import com.example.bookmanager.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.WebAsyncTask;

import java.util.concurrent.Callable;

@RestController
@RequestMapping("/api/books")
public class BookStatisticsController {

    private static final Logger logger = LoggerFactory.getLogger(BookStatisticsController.class);

    private final BookService bookService;
    private final ThreadPoolTaskExecutor webAsyncExecutor;

    public BookStatisticsController(BookService bookService,
                                    @Qualifier("webAsyncExecutor") ThreadPoolTaskExecutor webAsyncExecutor) {
        this.bookService = bookService;
        this.webAsyncExecutor = webAsyncExecutor;
    }

    @GetMapping("/statistics")
    public WebAsyncTask<Result<BookStatistics>> getStatistics() {
       logger.info("请求线程：" + Thread.currentThread().getName());

        // 创建 Callable 任务
        Callable<Result<BookStatistics>> callable = () -> {
            logger.info("异步执行线程：" + Thread.currentThread().getName());

            try {
                BookStatistics bookStatistics = bookService.getStatistics();
                return Result.success(bookStatistics);
            } catch (Exception e) {
                logger.error("统计失败", e);
                return Result.error(500, "统计服务暂时不可用: " + e.getMessage());
            }
        };

        // 创建WebAsyncTack，设置超时5秒
        WebAsyncTask<Result<BookStatistics>> asyncTask = new WebAsyncTask<>(5000L, webAsyncExecutor, callable);

        // 超时回调
        asyncTask.onTimeout(() -> {
            logger.warn("统计请求超时");
            return Result.error(504, "统计服务响应超时，请稍后重试");
        });

        // 异常回调
        asyncTask.onError(() -> {
            logger.error("统计请求异常");
            return Result.error(500, "统计服务异常");
        });

        // 完成回调
        asyncTask.onCompletion(() -> {
            logger.info("统计请求完成");
        });

        return asyncTask;
    }

    @GetMapping("/statistics-sync")
    public Result<BookStatistics> getStatisticsSync() {
        logger.info("[同步] 收到统计请求，请求线程: {}", Thread.currentThread().getName());
        BookStatistics statistics = bookService.getStatistics();
        return Result.success(statistics);
    }
}
