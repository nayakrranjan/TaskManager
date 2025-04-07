package com.taskmanager.repository;

import com.taskmanager.model.Approval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApprovalRepository extends JpaRepository<Approval, Long> {
    List<Approval> findByTaskId(Long taskId);
    List<Approval> findByApproverId(Long approverId);
    Optional<Approval> findByTaskIdAndApproverId(Long taskId, Long approverId);
}
