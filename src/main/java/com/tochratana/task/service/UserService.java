package com.tochratana.task.service;

import com.tochratana.task.domain.User;
import com.tochratana.task.dto.*;

import java.util.List;

public interface UserService {
    AuthResponseDto register(RegisterRequestDto registerRequestDto);
    AuthResponseDto login(LoginRequestDto loginRequestDto);
    UserResponse createUser(UserCreateRequestDto userCreateRequestDto);
    List<UserResponse> findAllUser();
    UserResponse getCurrentUser();
    User getCurrentUserEntity();
}