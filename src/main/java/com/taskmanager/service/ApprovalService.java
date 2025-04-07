package com.taskmanager.service;

import com.taskmanager.dto.ApprovalActionDTO;
import com.taskmanager.dto.ApprovalDTO;
import com.taskmanager.exception.ResourceNotFoundException;
import com.taskmanager.model.Approval;
import com.taskmanager.model.Task;
import com.taskmanager.model.User;
import com.taskmanager.repository.ApprovalRepository;
import com.taskmanager.repository.TaskRepository;
import com.taskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApprovalService {

    private final ApprovalRepository approvalRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    @Transactional
    public ApprovalDTO approveTask(Long taskId, Long approverId, ApprovalActionDTO approvalAction) {
        Approval approval = approvalRepository.findByTaskIdAndApproverId(taskId, approverId)
                .orElseThrow(() -> new ResourceNotFoundException("Approval request not found"));

        Task task = approval.getTask();
        User approver = approval.getApprover();

        if (approvalAction.isApproved()) {
            approval.approve(approvalAction.getComment());
        } else {
            approval.reject(approvalAction.getComment());
        }

        approvalRepository.save(approval);

        // Notify task creator of the approval action
        emailService.sendApprovalActionNotification(
                task.getCreator().getEmail(),
                approver.getUsername(),
                task.getTitle(),
                approvalAction.isApproved(),
                task.getId()
        );

        // If task is fully approved, notify all parties
        if (task.getStatus().toString().equals("APPROVED")) {
            for (Approval taskApproval : task.getApprovals()) {
                emailService.sendTaskCompletedNotification(
                        taskApproval.getApprover().getEmail(),
                        task.getTitle(),
                        task.getId()
                );
            }

            // Also notify the task creator
            emailService.sendTaskCompletedNotification(
                    task.getCreator().getEmail(),
                    task.getTitle(),
                    task.getId()
            );
        }

        return mapApprovalToDTO(approval);
    }

    @Transactional(readOnly = true)
    public List<ApprovalDTO> getApprovalsByTaskId(Long taskId) {
        return approvalRepository.findByTaskId(taskId).stream()
                .map(this::mapApprovalToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ApprovalDTO> getApprovalsByApproverId(Long approverId) {
        return approvalRepository.findByApproverId(approverId).stream()
                .map(this::mapApprovalToDTO)
                .collect(Collectors.toList());
    }

    private ApprovalDTO mapApprovalToDTO(Approval approval) {
        ApprovalDTO dto = new ApprovalDTO();
        dto.setId(approval.getId());
        dto.setTaskId(approval.getTask().getId());
        dto.setTaskTitle(approval.getTask().getTitle());
        dto.setApproverId(approval.getApprover().getId());
        dto.setApproverName(approval.getApprover().getUsername());
        dto.setStatus(approval.getStatus().toString());
        dto.setCreatedAt(approval.getCreatedAt());
        dto.setRespondedAt(approval.getRespondedAt());
        dto.setComment(approval.getComment());
        return dto;
    }
}
