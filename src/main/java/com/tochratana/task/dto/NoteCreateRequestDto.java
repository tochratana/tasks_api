package com.tochratana.task.dto;

import jakarta.validation.constraints.NotBlank;

public record NoteCreateRequestDto(
        @NotBlank(message = "Content is required")
        String content
) {}