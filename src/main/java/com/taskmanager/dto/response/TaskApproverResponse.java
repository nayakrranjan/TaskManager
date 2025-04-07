package com.taskmanager.dto.response;

import com.taskmanager.model.ApprovalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskApproverResponse {
    private Long taskApproverId;
    private Long taskId;
    private UserResponse approver;
    private ApprovalStatus approvalStatus;
    private LocalDateTime approvalDate;
    private String comments;
}
