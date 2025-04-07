package com.taskmanager.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "approvals")
@Data
@NoArgsConstructor
public class Approval {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @ManyToOne
    @JoinColumn(name = "approver_id", nullable = false)
    private User approver;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApprovalStatus status = ApprovalStatus.PENDING;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime respondedAt;

    private String comment;

    public Approval(Task task, User approver) {
        this.task = task;
        this.approver = approver;
    }

    public void approve(String comment) {
        this.status = ApprovalStatus.APPROVED;
        this.comment = comment;
        this.respondedAt = LocalDateTime.now();
        task.updateStatus();
    }

    public void reject(String comment) {
        this.status = ApprovalStatus.REJECTED;
        this.comment = comment;
        this.respondedAt = LocalDateTime.now();
        task.setStatus(TaskStatus.REJECTED);
    }
}