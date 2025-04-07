package com.taskmanager.dto;

import lombok.Data;

@Data
public class ApprovalActionDTO {
    private boolean approved;
    private String comment;
}
