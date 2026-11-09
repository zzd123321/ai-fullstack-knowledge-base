package com.example.bookmanager.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.WebAsyncTask;

import java.util.concurrent.Callable;

@RestController
@RequestMapping("/api/async")
public class AsyncWebController {

    @Autowired
    @Qualifier("coreExecutor")
    private ThreadPoolTaskExecutor webAsyncExecutor;

    @GetMapping("/callable")
    public Callable<String> callableDemo() {
        System.out.println("请求线程：" + Thread.currentThread().getName());

        return () -> {
            System.out.println("异步执行线程：" + Thread.currentThread().getName());

            // 模拟耗时操作
            Thread.sleep(3000);

            return "异步结果";
        };
    }

    @GetMapping("/web-async-task")
    public WebAsyncTask<String> webAsyncTask() {
        System.out.println("请求线程：" + Thread.currentThread().getName());

        // 创建Callable任务
        Callable<String> callable = () -> {
            System.out.println("异步执行线程：" + Thread.currentThread().getName());
            // 模拟耗时操作
            Thread.sleep(3000);
            return "WebAsyncTask 结果";
        };

//        WebAsyncTask<String> webAsyncTask = new WebAsyncTask<>(5000L, webAsyncExecutor, callable);
        WebAsyncTask<String> webAsyncTask = new WebAsyncTask<>(5000L, callable);

        // 超时回调
        webAsyncTask.onTimeout(() -> {
            System.out.println("任务超时");
            return "超时响应";
        });

        // 完成回调：无论成功、失败、异常都会执行
        webAsyncTask.onCompletion(() -> {
            System.out.println("任务完成（回调）");
        });

        webAsyncTask.onError(() -> {
            System.out.println("任务异常");
            return "异常响应";
        });

        return webAsyncTask;
    }

}
