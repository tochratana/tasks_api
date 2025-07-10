package com.tochratana.task.controller;


import com.tochratana.task.dto.UserCreateRequestDto;
import com.tochratana.task.dto.UserResponse;
import com.tochratana.task.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserResponse createNewUser(@Valid @RequestBody UserCreateRequestDto userCreateRequestDto){
        return userService.createUser(userCreateRequestDto);
    }

    @GetMapping
    public List<UserResponse> findAllUser(){
        return userService.findAllUser();
    }
}
