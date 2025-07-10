package com.tochratana.task.dto;

import com.tochratana.task.domain.Priority;
import com.tochratana.task.domain.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record TaskCreateRequestDto(
        @NotBlank(message = "Title is required")
        String title,

        String description,

        @NotNull(message = "Priority is required")
        Priority priority,

        TaskStatus status,

        LocalDateTime dueDate,

        List<String> noteContents
) {}