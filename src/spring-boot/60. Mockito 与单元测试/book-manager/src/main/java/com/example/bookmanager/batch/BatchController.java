package com.example.bookmanager.batch;

import com.example.bookmanager.model.Result;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/batch")
public class BatchController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier("importBookJob")
    private Job importBookJob;

    @Autowired
    @Qualifier("csvImportJob")
    private Job csvImportJob;

    @PostMapping("/import-books")
    public Result<String> importBooks() throws Exception {

        // 构建Job执行参数：使用时间戳作为参数，避免重复执行被拒绝
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("timestamp", System.currentTimeMillis())
                .toJobParameters();

        jobLauncher.run(importBookJob, jobParameters);
        return Result.success("批处理任务已启动");
    }

    @PostMapping("/import-csv")
    public Result<String> importCsv() throws Exception {

        // 构建Job执行参数：使用时间戳作为参数，避免重复执行被拒绝
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("timestamp", System.currentTimeMillis())
                .toJobParameters();

        jobLauncher.run(csvImportJob, jobParameters);
        return Result.success("CSV导入任务已启动");
    }
}
