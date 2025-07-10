package com.tochratana.task.service;

import com.tochratana.task.dto.UserCreateRequestDto;
import com.tochratana.task.dto.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserCreateRequestDto userCreateRequestDto);
    List<UserResponse> findAllUser();
}
