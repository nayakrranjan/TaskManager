package com.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentCreateDTO {
    @NotBlank(message = "Comment content is required")
    private String content;

    private Long taskId;
}
