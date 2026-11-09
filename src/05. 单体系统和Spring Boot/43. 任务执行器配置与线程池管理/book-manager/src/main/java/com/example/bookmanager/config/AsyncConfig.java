package com.example.bookmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class AsyncConfig {

    @Bean(name = "taskExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数：常驻线程数
        executor.setCorePoolSize(5);
        // 最大线程数：核心 + 非核心
        executor.setMaxPoolSize(20);
        // 队列容量：当核心线程满的时候，任务会进入队列
        executor.setQueueCapacity(100);
        // 非核心线程数的存活时间
        executor.setKeepAliveSeconds(60);
        // 线程名称前缀（便于监控）
        executor.setThreadNamePrefix("async-task-");
        // 拒绝策略：当线程池和队列都满的时候
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 优雅关闭：等待任务执行完成之后再关闭
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 等待超时时间
        executor.setAwaitTerminationSeconds(30);
        // 初始化线程池
        executor.initialize();
        return executor;
    }

    /**
     * 核心业务线程池（日志、通知等）
     */
    @Bean(name = "coreExecutor")
    public ThreadPoolTaskExecutor coreExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("core-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    /**
     * 报表/导出线程池（耗时长，独立资源）
     */
    @Bean(name = "reportExecutor")
    public ThreadPoolTaskExecutor reportExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(20);
        executor.setThreadNamePrefix("report-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        executor.initialize();
        return executor;
    }

    /**
     * 邮件/通知线程池（高延迟 I/O）
     */
    @Bean(name = "mailExecutor")
    public ThreadPoolTaskExecutor mailExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(200);
        executor.setThreadNamePrefix("mail-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.initialize();
        return executor;
    }


    @Bean(name = "customExecutor")
    public ThreadPoolTaskExecutor customExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("custom-");

        // 自定义拒绝策略：记录日志发送告警
        executor.setRejectedExecutionHandler((r, pool) -> {
            System.out.println("任务被拒绝，线程池已满！");
            // 可以发送报警邮件或者记录到监控系统

            throw new RejectedExecutionException("任务被拒绝，线程池已满！");
        });

        executor.initialize();
        return executor;
    }
}
