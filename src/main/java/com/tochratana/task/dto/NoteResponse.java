package com.tochratana.task.dto;

import java.time.LocalDateTime;

public record NoteResponse(
        Long id,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
