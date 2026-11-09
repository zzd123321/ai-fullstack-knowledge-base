package com.example.bookmanager.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class StepLoggingListener implements StepExecutionListener {
    private static final Logger logger = LoggerFactory.getLogger(StepLoggingListener.class);

    @Override
    public void beforeStep(StepExecution stepExecution) {
        logger.info("---------- 步骤开始 ----------");
        logger.info("步骤名称: {}", stepExecution.getStepName());
        logger.info("执行ID: {}", stepExecution.getJobExecutionId());
    }


    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        logger.info("---------- 步骤结束 ----------");
        logger.info("步骤名称: {}", stepExecution.getStepName());
        logger.info("状态: {}", stepExecution.getStatus());
        logger.info("读取数量: {}", stepExecution.getReadCount());
        logger.info("过滤数量: {}", stepExecution.getFilterCount());
        logger.info("写入数量: {}", stepExecution.getWriteCount());
        logger.info("提交次数: {}", stepExecution.getCommitCount());
        logger.info("回滚次数: {}", stepExecution.getRollbackCount());

        logger.info("耗时：{}ms", Duration.between(stepExecution.getStartTime(),
                stepExecution.getEndTime()).toMillis());

        // 返回null代表不修改退出状态
        return null;
    }
}
