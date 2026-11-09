package com.example.bookmanager.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;

@Component
public class ThreadPoolMonitor {

    @Autowired
    @Qualifier("taskExecutor")
    private ThreadPoolTaskExecutor executor;

    // 自动调度执行监控方法
    public void monitor() {
        ThreadPoolExecutor pool = executor.getThreadPoolExecutor();
        System.out.println("===== 线程池监控 =====");
        System.out.println("核心线程数: " + pool.getCorePoolSize());
        System.out.println("最大线程数: " + pool.getMaximumPoolSize());
        System.out.println("当前活跃线程: " + pool.getActiveCount());
        System.out.println("当前池大小: " + pool.getPoolSize());
        System.out.println("队列大小: " + pool.getQueue().size());
        System.out.println("已完成任务: " + pool.getCompletedTaskCount());
        System.out.println("任务总数: " + pool.getTaskCount());
    }
}