package com.taskmanager.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TaskApprovalRequest {
    @NotBlank(message = "Approval status is required")
    private String approvalStatus;

    private String comments;
}
