package com.example.bookmanager.scheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Service
public class DynamicTaskService {

    private static final Logger logger = LoggerFactory.getLogger(StockCheckService.class);

    private final TaskScheduler taskScheduler;

    // 保存当前正在运行的调度任务
    private final Map<String, ScheduledFuture<?>> runningTasks = new ConcurrentHashMap<>();

    public DynamicTaskService(@Qualifier("bookTaskScheduler") TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    // 启动一个定时任务
    public boolean startTask(String taskId, Runnable task, Duration period) {
        if(runningTasks.containsKey(taskId)) {
            logger.warn("任务{}已在运行", taskId );
            return false;
        }

        ScheduledFuture<?> future = taskScheduler.scheduleAtFixedRate(task, period);
        runningTasks.put(taskId, future);
        logger.info("任务{}已启动", taskId);
        return true;
    }

    // 停止一个定时任务
    public boolean stopTask(String taskId) {
        ScheduledFuture<?> future = runningTasks.remove(taskId);
        if(future == null) {
            logger.warn("任务{}不存在", taskId );
            return false;
        }

        boolean cancelled = future.cancel(false);
        logger.info("任务{}已取消:{}", taskId, cancelled);
        return cancelled;
    }

    // 查询任务是否运行
    public boolean isTaskRunning(String taskId) {
        ScheduledFuture<?> future = runningTasks.get(taskId);
        return future != null && !future.isCancelled() && !future.isDone();
    }

    // 获取所有运行中的日任务
    public Map<String, ScheduledFuture<?>> getRunningTasks() {
        return runningTasks;
    }
}
