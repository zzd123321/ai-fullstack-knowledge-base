package com.example.bookmanager.scheduling;

import com.example.bookmanager.model.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stock")
public class StockCheckController {

    private final StockCheckService stockCheckService;

    public StockCheckController(StockCheckService stockCheckService) {
        this.stockCheckService = stockCheckService;
    }

    @PostMapping("/start")
    public Result<String> start() {
        stockCheckService.startIncrementalCheck();
        return Result.success("增量盘点已启动");
    }

    @PostMapping("/stop")
    public Result<String> stop() {
        stockCheckService.stopIncrementalCheck();
        return Result.success("增量盘点已停止");
    }

    @GetMapping("/status")
    public Result<Boolean> status() {
        return Result.success(stockCheckService.isIncrementalCheckRunning());
    }
}