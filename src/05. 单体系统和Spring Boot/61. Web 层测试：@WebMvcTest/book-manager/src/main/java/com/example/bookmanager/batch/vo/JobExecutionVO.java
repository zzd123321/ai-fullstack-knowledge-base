package com.example.bookmanager.batch.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class JobExecutionVO {
    private Long executionId;
    private Long instanceId;
    private String jobName;
    private String status;
    private String exitCode;
    private String exitMessage;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String duration;
}