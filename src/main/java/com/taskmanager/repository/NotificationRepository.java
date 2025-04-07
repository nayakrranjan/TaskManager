package com.taskmanager.repository;

import com.taskmanager.model.Notification;
import com.taskmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserAndIsReadOrderByCreatedAtDesc(User user, Boolean isRead);
}