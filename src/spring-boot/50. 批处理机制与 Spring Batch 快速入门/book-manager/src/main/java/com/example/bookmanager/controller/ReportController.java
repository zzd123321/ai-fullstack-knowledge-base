package com.example.bookmanager.controller;

import com.example.bookmanager.model.Result;
import com.example.bookmanager.service.ReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/books")
    public Result<String> generateBookReport() {
        CompletableFuture<String> future = reportService.generateBookReport();

        // 不阻塞，直接返回
        return Result.success("报表生成任务已提交，请稍后查看");
    }

    @GetMapping("/books/wait")
    public Result<String> generateBookReportWait() throws Exception {
        CompletableFuture<String> future = reportService.generateBookReport();

        // 阻塞等待结果
        String filePath = future.get(30, TimeUnit.SECONDS);
        if(filePath.startsWith("ERROR")) {
            return Result.error(500, filePath);
        }

        return Result.success("报表已生成：" + filePath);
    }

    @GetMapping("/books/async")
    public CompletableFuture<Result<?>> generateBookReportAsync() {
        return reportService.generateBookReport()
                .thenApply(filePath -> {
                    if(filePath.startsWith("ERROR")) {
                        return Result.error(500, filePath);
                    }
                    return Result.success("报表已生成：" + filePath);
                })
                .exceptionally(ex -> Result.error(500, "报表生成失败：" + ex.getMessage()));

    }
}