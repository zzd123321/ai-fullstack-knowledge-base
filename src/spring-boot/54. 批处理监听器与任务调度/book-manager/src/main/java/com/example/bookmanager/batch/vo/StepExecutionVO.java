package com.example.bookmanager.batch.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StepExecutionVO {
    private Long stepExecutionId;
    private String stepName;
    private String status;
    private Long readCount;
    private Long writeCount;
    private Long commitCount;
    private Long rollbackCount;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}