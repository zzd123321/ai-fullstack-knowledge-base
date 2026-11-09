package com.example.bookmanager.batch;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobExecutionNotRunningException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class JobAdminService {

    private final JobOperator jobOperator;
    private final JobExplorer jobExplorer;

    public JobAdminService(JobOperator jobOperator, JobExplorer jobExplorer) {
        this.jobOperator = jobOperator;
        this.jobExplorer = jobExplorer;
    }

    // 停止正在运行的作业
    public boolean stopJob(Long executionId) {
        try {
            boolean result = jobOperator.stop(executionId);
            System.out.println("停止作业：" + executionId + "， 结果：" + result);
            return result;
        } catch (Exception e) {
            throw new RuntimeException("停止作业失败", e);
        }
    }

    // 重启失败的作业
    public Long restartJob(Long executionId) {

        try {
            // 检查作业是否可以重启
            JobExecution jobExecution = jobExplorer.getJobExecution(executionId);
            if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
                throw new RuntimeException("作业已完成，无需重启");
            }
            if (jobExecution.getStatus() == BatchStatus.STARTED) {
                throw new RuntimeException("作业正在运行，无需重启");
            }

            Long newExectionId = jobOperator.restart(executionId);
            System.out.println("重启作业：" + executionId + " -> " + newExectionId);
            return newExectionId;
        } catch (Exception e) {
            throw new RuntimeException("重启作业失败", e);
        }
    }

    // 获取作业的摘要信息
    public String getJobSummary(Long executionId) {
        try {
            return jobOperator.getSummary(executionId);
        } catch (Exception e) {
            throw new RuntimeException("获取摘要失败", e);
        }
    }

    // 查询正在运行的作业执行Id
    public Set<Long> getRunningExecutions(String jobName) {
        try {
            return jobOperator.getRunningExecutions(jobName);
        } catch (Exception e) {
            throw new RuntimeException("查询运行中作业失败", e);
        }
    }
}
