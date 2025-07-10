package com.tochratana.task.service.impl;

import com.tochratana.task.domain.User;
import com.tochratana.task.dto.UserCreateRequestDto;
import com.tochratana.task.dto.UserResponse;
import com.tochratana.task.repository.UserRepository;
import com.tochratana.task.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public UserResponse createUser(UserCreateRequestDto userCreateRequestDto) {

        // Step 1: Map from RequestDto → Entity
        User user = new User();
        user.setFullName(userCreateRequestDto.fullName());
        user.setEmail(userCreateRequestDto.email());
        user.setPassword(userCreateRequestDto.password());

        // Step 2: Save user
        user = userRepository.save(user);

        // Step 3: Map from Entity → ResponseDto
        return new UserResponse(
                user.getFullName(),
                user.getEmail()
        );
    }

    @Override
    public List<UserResponse> findAllUser() {
        // create a list for user
        List<User> users = userRepository.findAll();

        // create a list for user response data
        List<UserResponse> userResponseList = users.stream().map(user ->
                new UserResponse(
                        user.getFullName(),
                        user.getEmail()
                )).toList();

        return userResponseList;
    }
}
