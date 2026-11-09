package com.example.bookmanager.batch.scheduling;

import com.example.bookmanager.model.Result;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/batch")
public class StatisticsBatchController {

    private final JobLauncher jobLauncher;
    private final Job statisticsJob;

    public StatisticsBatchController(JobLauncher jobLauncher,
                                     @Qualifier("statisticsJob") Job statisticsJob) {
        this.jobLauncher = jobLauncher;
        this.statisticsJob = statisticsJob;
    }

    @PostMapping("/statistics")
    public Result<String> runStatistics() throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addLong("timestamp", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(statisticsJob, params);
        return Result.success("统计任务已启动");
    }
}