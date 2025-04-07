package com.taskmanager.repository;

import com.taskmanager.model.ApprovalStatus;
import com.taskmanager.model.Task;
import com.taskmanager.model.TaskApprover;
import com.taskmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskApproverRepository extends JpaRepository<TaskApprover, Long> {
    List<TaskApprover> findByTask(Task task);
    List<TaskApprover> findByApprover(User approver);
    Optional<TaskApprover> findByTaskAndApprover(Task task, User approver);

    @Query("SELECT COUNT(ta) FROM TaskApprover ta WHERE ta.task = ?1 AND ta.approvalStatus = ?2")
    Long countByTaskAndApprovalStatus(Task task, ApprovalStatus status);
}