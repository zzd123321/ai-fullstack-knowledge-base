package com.example.bookmanager.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Service
public class AsyncTaskService {

    @Async("reportExecutor")
    public Future<String> generateReport() {
        // 模拟耗时报表的生成
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // 使用 AsyncResult 包装返回值
        return new AsyncResult<>("报表内容：2026年销售数据");

    }

    @Async("reportExecutor")
    public CompletableFuture<String> generateReportWithCompletable() {
//        // 模拟耗时报表的生成
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//
//        // 使用 AsyncResult 包装返回值
//        return CompletableFuture.completedFuture("报表内容：2026年销售数据");

        // 第一步：生成报表数据（耗时）
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "原始报表数据";
        }).thenApply(data -> {
            // 第二步：格式化
            return data.toUpperCase();
        }).thenApply(data -> {
            // 第三步：添加标题
            return "===销售报表===\n" + data;
        }).thenApply(data -> {
            // 第四步：添加时间戳
            return data + "\n生成时间：" + LocalDateTime.now();
        });
    }

    @Async("reportExecutor")
    public CompletableFuture<String> fetchSalesData() {
        // 模拟获取销售数据
        return CompletableFuture.completedFuture("销售数据: 1000万");
    }

    @Async("reportExecutor")
    public CompletableFuture<String> fetchUserData() {
        // 模拟获取用户数据
        return CompletableFuture.completedFuture("用户数据: 5000人");
    }

    @Async("reportExecutor")
    public CompletableFuture<String> fetchProductData() {
        // 模拟获取产品数据
        return CompletableFuture.completedFuture("产品数据: 200种");
    }

    public CompletableFuture<String> generateFullReport() {
        CompletableFuture<String> sales = fetchSalesData();
        CompletableFuture<String> users = fetchUserData();
        CompletableFuture<String> products = fetchProductData();

        // 等待所有任务完成，然后汇总
        return CompletableFuture.allOf(sales, users, products)
                .thenApply( v -> {
                    String result = sales.join() + "\n" +
                            users.join() + "\n" +
                            products.join();
                    return "===综合报表===\n" + result;
                });
    }

    public CompletableFuture<String> generateReportWithHandle() {
        return CompletableFuture.supplyAsync(() -> {
            if(Math.random() > 0.5) {
                throw new RuntimeException("处理失败");
            }
            return "报表成功";
        }).handle((result, ex) -> {
            if(ex != null) {
                return "错误报表：" + ex.getMessage();
            }

            return "正常报表：" + result;
        });
    }
}
