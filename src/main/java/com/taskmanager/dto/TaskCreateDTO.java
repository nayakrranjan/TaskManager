package com.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class TaskCreateDTO {
    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotEmpty(message = "At least 3 approvers are required")
    @Size(min = 3, message = "At least 3 approvers are required")
    private List<Long> approverIds;
}
