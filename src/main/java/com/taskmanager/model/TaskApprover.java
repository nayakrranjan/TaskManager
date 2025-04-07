package com.taskmanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "task_approvers", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"task_id", "approver_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskApprover {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_approver_id")
    private Long taskApproverId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approver_id", nullable = false)
    private User approver;

    @Enumerated(EnumType.STRING)
    @Column(name = "approval_status", nullable = false)
    private ApprovalStatus approvalStatus = ApprovalStatus.PENDING;

    @Column(name = "approval_date")
    private LocalDateTime approvalDate;

    @Column
    private String comments;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;
}