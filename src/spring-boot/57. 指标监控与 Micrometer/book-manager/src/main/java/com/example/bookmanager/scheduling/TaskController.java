package com.example.bookmanager.scheduling;

import com.example.bookmanager.model.Result;
import com.example.bookmanager.scheduling.cron.BookScheduledTasksWithCron;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final DynamicTaskService dynamicTaskService;

    public TaskController(DynamicTaskService dynamicTaskService) {
        this.dynamicTaskService = dynamicTaskService;
    }

    @PostMapping("/start")
    public Result<String> startTask(@RequestParam String taskId,
                                    @RequestParam(defaultValue = "10") int intervalSeconds) {
        Runnable task = () -> System.out.println("执行任务" + taskId);

        boolean started = dynamicTaskService.startTask(taskId, task, Duration.ofSeconds(intervalSeconds));

        return started ? Result.success("任务已启动") : Result.error(400, "任务已存在");
    }

    @PostMapping("/stop")
    public Result<String> stopTask(@RequestParam String taskId) {
        boolean stopped = dynamicTaskService.stopTask(taskId);
        return stopped ? Result.success("任务已停止") : Result.error(404, "任务不存在");
    }

    @GetMapping("/status")
    public Result<Boolean> status(@RequestParam String taskId) {
        return Result.success(dynamicTaskService.isTaskRunning(taskId));
    }

    @Autowired
    private BookScheduledTasksWithCron tasks;

    /**
     * 手动触发日报生成（用于测试）
     */
    @PostMapping("/daily-report")
    public Result<String> triggerDailyReport() {
        tasks.generateDailyReport();
        return Result.success("日报生成任务已触发");
    }

    /**
     * 手动触发周报生成
     */
    @PostMapping("/weekly-report")
    public Result<String> triggerWeeklyReport() {
        tasks.generateWeeklyReport();
        return Result.success("周报生成任务已触发");
    }

    /**
     * 手动触发月报生成
     */
    @PostMapping("/monthly-report")
    public Result<String> triggerMonthlyReport() {
        tasks.generateMonthlyReport();
        return Result.success("月报生成任务已触发");
    }
}
