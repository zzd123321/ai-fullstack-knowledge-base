package com.example.bookmanager.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableScheduling
public class SchedulerConfig {
    private static final Logger logger = LoggerFactory.getLogger(AsyncConfig.class);


    @Bean(name = "bookTaskScheduler")
    public TaskScheduler taskScheduler() {

        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        // 指定同时执行任务的数量
        taskScheduler.setPoolSize(5);
        taskScheduler.setThreadNamePrefix("book-scheduler-");
        // 取消任务时是否从队列中移除
        taskScheduler.setRemoveOnCancelPolicy(true);
        taskScheduler.setWaitForTasksToCompleteOnShutdown(true);
        taskScheduler.setAwaitTerminationSeconds(30);

        taskScheduler.setErrorHandler(t -> {
            logger.error("调度任务异常: {}", t.getMessage(), t);
        });

        taskScheduler.initialize();
        return taskScheduler;
    }
}
