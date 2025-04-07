package com.taskmanager.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status = TaskStatus.PENDING;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime completedAt;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Approval> approvals = new HashSet<>();

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();

    public Task(String title, String description, User creator) {
        this.title = title;
        this.description = description;
        this.creator = creator;
    }

    // Method to check if task is fully approved
    public boolean isFullyApproved() {
        return approvals.stream()
                .filter(approval -> approval.getStatus() == ApprovalStatus.APPROVED)
                .count() >= 3;
    }

    // Update task status based on approvals
    public void updateStatus() {
        if (isFullyApproved()) {
            this.status = TaskStatus.APPROVED;
            this.completedAt = LocalDateTime.now();
        }
    }
}
