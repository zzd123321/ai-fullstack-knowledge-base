package com.example.bookmanager.batch;

import com.example.bookmanager.batch.vo.JobExecutionVO;
import com.example.bookmanager.batch.vo.StepExecutionVO;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class JobHistoryService {

    private final JobExplorer jobExplorer;

    public JobHistoryService(JobExplorer jobExplorer) {
        this.jobExplorer = jobExplorer;
    }

//    // 查询指定作业的所有实例
//    public List<JobInstance> getJobInstances(String jobName) {
//        return jobExplorer.getJobInstances(jobName, 0, 100);
//    }
//
//    // 查询作业的实例数量
//    public Long getInstanceCount(String jobName) {
//        try {
//            return jobExplorer.getJobInstanceCount(jobName);
//        } catch (NoSuchJobException e) {
//            throw new RuntimeException("查询失败：" + e.getMessage(), e);
//        }
//    }
//
//    // 查询指定作业实例的所有执行记录
//    public List<JobExecution> getJobExecutions(String jobName, Long instanceId) {
//        JobInstance jobInstance = jobExplorer.getJobInstance(instanceId);
//        return jobExplorer.getJobExecutions(jobInstance);
//    }
//
//    // 查询正在运行的作业
//    public Set<JobExecution> getRunningJobExecutions(String jobName) {
//        return jobExplorer.findRunningJobExecutions(jobName);
//    }
//
//    // 查询指定作业执行的所有步骤
//    public List<StepExecution> getStepExecutions(Long jobExecutionId) {
//        // 不同版本的写法有所不同
//        JobExecution jobExecution = jobExplorer.getJobExecution(jobExecutionId);
//        return new ArrayList<>(jobExecution.getStepExecutions());
//    }


    // 查询指定作业的所有执行记录
    public List<JobExecutionVO> getJobExecutions(String jobName) {
        List<JobInstance> instances = jobExplorer.getJobInstances(jobName, 0, 100);
        if(instances.isEmpty()) {
            return Collections.emptyList();
        }

        return instances.stream()
                .flatMap(instance -> jobExplorer.getJobExecutions(instance).stream())
                        .map(this::toVO)
                        .collect(Collectors.toList());
    }

    // 查询执行步骤
    public List<StepExecutionVO> getStepExecutions(Long executionId) {
        JobExecution jobExecution = jobExplorer.getJobExecution(executionId);

        return jobExecution.getStepExecutions().stream()
                .map(this::toVO)
                .collect(Collectors.toList());
    }

    // 查询正在运行的执行Id
    public Set<Long> getRunningExecutions(String jobName) {
        return jobExplorer.findRunningJobExecutions(jobName).stream()
                .map(JobExecution::getId)
                .collect(Collectors.toSet());
    }

    // 查询指定作业的实例数量
    public Long getInstanceCount(String jobName) {
        try {
            return jobExplorer.getJobInstanceCount(jobName);
        } catch (NoSuchJobException e) {
            throw new RuntimeException("查询失败：" + e.getMessage(), e);
        }
    }

    // ========== 转换方法 ==========

    private JobExecutionVO toVO(JobExecution execution) {
        JobExecutionVO vo = new JobExecutionVO();
        vo.setExecutionId(execution.getId());
        vo.setInstanceId(execution.getJobInstance().getId());
        vo.setJobName(execution.getJobInstance().getJobName());
        vo.setStatus(execution.getStatus().name());
        vo.setExitCode(execution.getExitStatus().getExitCode());
        vo.setExitMessage(execution.getExitStatus().getExitDescription());

        execution.getStartTime();
        vo.setStartTime(LocalDateTime.from(execution.getStartTime()));
        execution.getEndTime();
        vo.setEndTime(LocalDateTime.from(execution.getEndTime()));

        // 计算耗时
        Duration duration = Duration.between(execution.getStartTime(), execution.getEndTime());
        vo.setDuration(duration.getSeconds() + "s");

        return vo;
    }

    private StepExecutionVO toVO(StepExecution stepExecution) {
        StepExecutionVO vo = new StepExecutionVO();
        vo.setStepExecutionId(stepExecution.getId());
        vo.setStepName(stepExecution.getStepName());
        vo.setStatus(stepExecution.getStatus().name());
        vo.setReadCount(stepExecution.getReadCount());
        vo.setWriteCount(stepExecution.getWriteCount());
        vo.setCommitCount(stepExecution.getCommitCount());
        vo.setRollbackCount(stepExecution.getRollbackCount());

        stepExecution.getStartTime();
        vo.setStartTime(LocalDateTime.from(stepExecution.getStartTime()));
        stepExecution.getEndTime();
        vo.setEndTime(LocalDateTime.from(stepExecution.getEndTime()));

        return vo;
    }
}
