package com.example.bookmanager.batch.scheduling;

import com.example.bookmanager.batch.listener.ItemLoggingListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class StatisticsJobScheduler {

    private static final Logger logger = LoggerFactory.getLogger(StatisticsJobScheduler.class);

    private final JobLauncher jobLauncher;
    private final Job statisticsJob;

    public StatisticsJobScheduler(JobLauncher jobLauncher,
                                  @Qualifier("statisticsJob") Job statisticsJob) {
        this.jobLauncher = jobLauncher;
        this.statisticsJob = statisticsJob;
    }

    // 每天凌晨2点执行
    @Scheduled(cron = "0 0 2 * * ?")
    public void ruanStatisticsJob() {
        logger.info("定时触发统计任务");

        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("timestamp", System.currentTimeMillis())
                .toJobParameters();
        try {
            jobLauncher.run(statisticsJob, jobParameters);
        } catch (Exception e) {
            logger.error("统计任务执行失败", e);

            // 可以在这里发送报警信息
        }
    }

}
