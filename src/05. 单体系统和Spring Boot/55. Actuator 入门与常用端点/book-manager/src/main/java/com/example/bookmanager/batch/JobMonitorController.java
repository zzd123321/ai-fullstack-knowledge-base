package com.example.bookmanager.batch;

import com.example.bookmanager.batch.vo.JobExecutionVO;
import com.example.bookmanager.batch.vo.StepExecutionVO;
import com.example.bookmanager.model.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/batch/jobs")
public class JobMonitorController {

    private final JobHistoryService jobHistoryService;
    private final JobAdminService jobAdminService;

    public JobMonitorController(JobHistoryService jobHistoryService,
                                JobAdminService jobAdminService) {
        this.jobHistoryService = jobHistoryService;
        this.jobAdminService = jobAdminService;
    }

    // 查询指定作业的所有执行记录
    @GetMapping("/{jobName}/executions")
    public Result<List<JobExecutionVO>> getExecutions(@PathVariable String jobName) {
        return Result.success(jobHistoryService.getJobExecutions(jobName));
    }


    /**
     * 查询指定执行的步骤详情
     * GET /api/batch/jobs/executions/1/steps
     */
    @GetMapping("/executions/{executionId}/steps")
    public Result<List<StepExecutionVO>> getSteps(@PathVariable Long executionId) {
        return Result.success(jobHistoryService.getStepExecutions(executionId));
    }

    /**
     * 查询正在运行的执行
     * GET /api/batch/jobs/csvImportJob/running
     */
    @GetMapping("/{jobName}/running")
    public Result<Set<Long>> getRunning(@PathVariable String jobName) {
        return Result.success(jobAdminService.getRunningExecutions(jobName));
    }

    /**
     * 查询作业实例数量
     * GET /api/batch/jobs/csvImportJob/count
     */
    @GetMapping("/{jobName}/count")
    public Result<Long> getCount(@PathVariable String jobName) {
        return Result.success(jobHistoryService.getInstanceCount(jobName));
    }

    /**
     * 停止正在运行的作业
     * POST /api/batch/jobs/executions/1/stop
     */
    @PostMapping("/executions/{executionId}/stop")
    public Result<String> stopJob(@PathVariable Long executionId) {
        boolean stopped = jobAdminService.stopJob(executionId);
        return stopped ? Result.success("作业已停止") : Result.error(400, "停止失败");
    }

    /**
     * 重启失败的作业
     * POST /api/batch/jobs/executions/1/restart
     */
    @PostMapping("/executions/{executionId}/restart")
    public Result<String> restartJob(@PathVariable Long executionId) {
        Long newExecutionId = jobAdminService.restartJob(executionId);
        return Result.success("作业已重启，新执行ID: " + newExecutionId);
    }
}