package com.tochratana.task.dto;

import com.tochratana.task.domain.Priority;
import com.tochratana.task.domain.TaskStatus;

import java.time.LocalDateTime;
import java.util.List;

public record TaskResponse(
        Long id,
        String title,
        String description,
        TaskStatus status,
        LocalDateTime dueDate,
        Priority priority,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<NoteResponse> notes
) {}