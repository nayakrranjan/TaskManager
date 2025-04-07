package com.taskmanager.service;

import com.taskmanager.dto.CommentCreateDTO;
import com.taskmanager.dto.CommentDTO;
import com.taskmanager.exception.ResourceNotFoundException;
import com.taskmanager.model.Comment;
import com.taskmanager.model.Task;
import com.taskmanager.model.User;
import com.taskmanager.repository.CommentRepository;
import com.taskmanager.repository.TaskRepository;
import com.taskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    @Transactional
    public CommentDTO addComment(CommentCreateDTO commentCreateDTO, Long userId) {
        Task task = taskRepository.findById(commentCreateDTO.getTaskId())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + commentCreateDTO.getTaskId()));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        Comment comment = new Comment(
                commentCreateDTO.getContent(),
                task,
                user
        );

        Comment savedComment = commentRepository.save(comment);

        // Notify task creator about the new comment if the commenter is not the creator
        if (!user.getId().equals(task.getCreator().getId())) {
            emailService.sendCommentNotification(
                    task.getCreator().getEmail(),
                    user.getUsername(),
                    task.getTitle(),
                    comment.getContent(),
                    task.getId()
            );
        }

        return mapCommentToDTO(savedComment);
    }

    @Transactional(readOnly = true)
    public List<CommentDTO> getCommentsByTaskId(Long taskId) {
        return commentRepository.findByTaskIdOrderByCreatedAtDesc(taskId).stream()
                .map(this::mapCommentToDTO)
                .collect(Collectors.toList());
    }

    private CommentDTO mapCommentToDTO(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setTaskId(comment.getTask().getId());
        dto.setUserId(comment.getUser().getId());
        dto.setUserName(comment.getUser().getUsername());
        return dto;
    }
}
