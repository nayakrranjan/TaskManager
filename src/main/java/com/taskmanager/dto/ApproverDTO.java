package com.taskmanager.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApproverDTO {
    private Long id;
    private String name;
    private String status;
    private LocalDateTime respondedAt;
}
