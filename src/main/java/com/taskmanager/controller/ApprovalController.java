package com.taskmanager.controller;

import com.taskmanager.dto.ApprovalActionDTO;
import com.taskmanager.dto.ApprovalDTO;
import com.taskmanager.security.CustomUserDetails;
import com.taskmanager.service.ApprovalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/V1/approvals")
@RequiredArgsConstructor
public class ApprovalController {

    private final ApprovalService approvalService;

    @PostMapping("/tasks/{taskId}")
    public ResponseEntity<ApprovalDTO> approveOrRejectTask(
            @PathVariable Long taskId,
            @Valid @RequestBody ApprovalActionDTO approvalAction,
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = ((CustomUserDetails) userDetails).getUserId();
        ApprovalDTO approvalDTO = approvalService.approveTask(taskId, userId, approvalAction);
        return ResponseEntity.ok(approvalDTO);
    }

    @GetMapping("/tasks/{taskId}")
    public ResponseEntity<List<ApprovalDTO>> getApprovalsByTaskId(@PathVariable Long taskId) {
        return ResponseEntity.ok(approvalService.getApprovalsByTaskId(taskId));
    }

    @GetMapping("/my-approvals")
    public ResponseEntity<List<ApprovalDTO>> getMyApprovals(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = ((CustomUserDetails) userDetails).getUserId();
        return ResponseEntity.ok(approvalService.getApprovalsByApproverId(userId));
    }
}

