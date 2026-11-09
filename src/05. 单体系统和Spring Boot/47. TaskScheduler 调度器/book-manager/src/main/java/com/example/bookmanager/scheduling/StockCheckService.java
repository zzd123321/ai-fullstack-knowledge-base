package com.example.bookmanager.scheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class StockCheckService {

    private static final Logger logger = LoggerFactory.getLogger(StockCheckService.class);

    private final TaskScheduler taskScheduler;

    private final AtomicReference<ScheduledFuture<?>> incrementalTask = new AtomicReference<>();

    public StockCheckService(TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void scheduleInitialStockCheck() {
        logger.info("注册库存盘点任务，10秒之后自动执行...");

        // 使用 TaskScheduler 提交延迟任务
        taskScheduler.schedule(
                this::doInitialStockCheck,
                Instant.now().plus(10, ChronoUnit.SECONDS)
        );
    }

    public void doInitialStockCheck() {
        logger.info("开始盘点库存，线程：{}", Thread.currentThread().getName());

        // 模拟判断逻辑
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        logger.info("初始化库存盘点完成，启动增量盘点");

        // 自动启动增量盘点
        startIncrementalCheck();
    }


    public synchronized void startIncrementalCheck() {
        if(incrementalTask.get() != null && !incrementalTask.get().isCancelled()) {
            logger.warn("增量盘点已在运行中");
            return;
        }

        ScheduledFuture<?> future = taskScheduler.scheduleAtFixedRate(
                this::doIncrementalStockCheck,
                Duration.ofSeconds(30)
        );
        incrementalTask.set(future);
        logger.info("增量盘点已启动（每30秒）");
    }

    public synchronized void stopIncrementalCheck() {
        ScheduledFuture<?> future = incrementalTask.getAndSet(null);
        if(future != null) {
            future.cancel(false);
            logger.info("增量盘点已停止");
        }
    }

    public boolean isIncrementalCheckRunning() {
        ScheduledFuture<?> future = incrementalTask.get();
        return future != null && !future.isCancelled() && !future.isDone();
    }

    public void doIncrementalStockCheck() {
        logger.info("增量盘点的线程: {}", Thread.currentThread().getName());
    }
}
