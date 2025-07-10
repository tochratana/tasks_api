package com.tochratana.task.dto;

public record AuthResponseDto(
        String token,
        String tokenType,
        UserResponse user
) {}
