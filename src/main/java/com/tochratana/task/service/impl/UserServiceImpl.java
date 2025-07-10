package com.tochratana.task.service.impl;

import com.tochratana.task.domain.User;
import com.tochratana.task.dto.*;
import com.tochratana.task.repository.UserRepository;
import com.tochratana.task.service.UserService;
import com.tochratana.task.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public AuthResponseDto register(RegisterRequestDto registerRequestDto) {
        if (userRepository.existsByEmail(registerRequestDto.email())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setFullName(registerRequestDto.fullName());
        user.setEmail(registerRequestDto.email());
        user.setPassword(passwordEncoder.encode(registerRequestDto.password()));

        user = userRepository.save(user);

        String token = jwtUtil.generateToken(user.getEmail());

        UserResponse userResponse = new UserResponse(user.getFullName(), user.getEmail());

        return new AuthResponseDto(token, "Bearer", userResponse);
    }

    @Override
    public AuthResponseDto login(LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.email(),
                        loginRequestDto.password()
                )
        );

        String token = jwtUtil.generateToken(loginRequestDto.email());

        User user = userRepository.findByEmail(loginRequestDto.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserResponse userResponse = new UserResponse(user.getFullName(), user.getEmail());

        return new AuthResponseDto(token, "Bearer", userResponse);
    }

    @Override
    public UserResponse createUser(UserCreateRequestDto userCreateRequestDto) {
        User user = new User();
        user.setFullName(userCreateRequestDto.fullName());
        user.setEmail(userCreateRequestDto.email());
        user.setPassword(passwordEncoder.encode(userCreateRequestDto.password()));

        user = userRepository.save(user);

        return new UserResponse(user.getFullName(), user.getEmail());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> findAllUser() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserResponse(user.getFullName(), user.getEmail()))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getCurrentUser() {
        User user = getCurrentUserEntity();
        return new UserResponse(user.getFullName(), user.getEmail());
    }

    @Override
    @Transactional(readOnly = true)
    public User getCurrentUserEntity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}