package com.tochratana.task.dto;

public record UserCreateRequestDto(
        String fullName,
        String email,
        String password
) {
}
