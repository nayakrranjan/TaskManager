package com.taskmanager.controller;

import com.taskmanager.dto.CommentCreateDTO;
import com.taskmanager.dto.CommentDTO;
import com.taskmanager.security.CustomUserDetails;
import com.taskmanager.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/V1/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDTO> addComment(
            @Valid @RequestBody CommentCreateDTO commentCreateDTO,
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = ((CustomUserDetails) userDetails).getUserId();
        CommentDTO commentDTO = commentService.addComment(commentCreateDTO, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentDTO);
    }

    @GetMapping("/tasks/{taskId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByTaskId(@PathVariable Long taskId) {
        return ResponseEntity.ok(commentService.getCommentsByTaskId(taskId));
    }
}
