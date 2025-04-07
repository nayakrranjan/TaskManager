package com.taskmanager.repository;

import com.taskmanager.model.Task;
import com.taskmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByCreator(User creator);
    List<Task> findByCreatorId(Long id);

    @Query("SELECT t FROM Task t JOIN t.approvals a WHERE a.approver.Id = :approverId")
    List<Task> findTasksAwaitingApproval(@Param("approverId") Long approverId);
}