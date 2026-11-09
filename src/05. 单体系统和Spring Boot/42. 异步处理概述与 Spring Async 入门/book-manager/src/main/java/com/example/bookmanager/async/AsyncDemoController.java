package com.example.bookmanager.async;

import com.example.bookmanager.model.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/demo")
public class AsyncDemoController {

    private final AsyncLogService asyncLogService;

    public AsyncDemoController(AsyncLogService asyncLogService) {
        this.asyncLogService = asyncLogService;
    }

    @GetMapping("/async-log")
    public Result<String> asyncLog(@RequestParam String message) {

        long startTime = System.currentTimeMillis();

        // 调用异步方法，立即返回
        asyncLogService.logOperation(message);

        long elapsed = System.currentTimeMillis() - startTime;

        return Result.success("异步任务已提交，耗时：" + elapsed + "ms, 当前线程：" +
                Thread.currentThread().getName());
    }

    @GetMapping("/sync-log")
    public Result<String> syncLog(@RequestParam String message) {
        long startTime = System.currentTimeMillis();

        // 调用同步方法，阻塞等待
        asyncLogService.logOperationSync(message);

        long elapsed = System.currentTimeMillis() - startTime;

        return Result.success("同步任务已提交，耗时：" + elapsed + "ms, 当前线程：" +
                Thread.currentThread().getName());
    }

    @GetMapping("/test")
    public String test() {
        asyncLogService.logWrapper("test");
        return "done";
    }
}
