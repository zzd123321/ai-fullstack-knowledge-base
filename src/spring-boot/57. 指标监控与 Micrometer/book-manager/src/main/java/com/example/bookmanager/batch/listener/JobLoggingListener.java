package com.example.bookmanager.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class JobLoggingListener implements JobExecutionListener {

    private static final Logger logger = LoggerFactory.getLogger(JobLoggingListener.class);
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void beforeJob(JobExecution jobExecution) {
        String jobName = jobExecution.getJobInstance().getJobName();
        logger.info("==========作业开始===========");
        logger.info("作业名称：{}", jobName);
        logger.info("执行Id：{}", jobExecution.getId());
        logger.info("开始时间：{}", LocalDateTime.now().format(FORMATTER));
        logger.info("作业参数：{}", jobExecution.getJobParameters());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        String jobName = jobExecution.getJobInstance().getJobName();

        long duration = Duration.between(
                jobExecution.getStartTime(),
                jobExecution.getEndTime()
        ).toMillis();

        logger.info("==========作业结束===========");
        logger.info("作业名称：{}", jobName);
        logger.info("执行状态：{}", jobExecution.getStatus());
        logger.info("退出码：{}", jobExecution.getExitStatus().getExitCode());
        logger.info("结束时间：{}", LocalDateTime.now().format(FORMATTER));
        logger.info("耗时：{}ms", duration);

        // 失败时告警
        if(jobExecution.getStatus().isUnsuccessful()) {
            logger.error("作业执行失败!失败原因：{}", jobExecution.getExitStatus().getExitDescription());
            // 可以在这里发送告警邮件或通知
        }
    }
}
