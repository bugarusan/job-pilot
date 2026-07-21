package com.example.jobpilot.service;

import com.example.jobpilot.dto.request.RegisterRequest;
import com.example.jobpilot.dto.request.UpdateUserRequest;
import com.example.jobpilot.dto.response.UserResponse;
import com.example.jobpilot.entity.User;

public interface UserService {
    UserResponse register(RegisterRequest request);

    UserResponse getById(Long id);

    UserResponse updateProfile(Long id, UpdateUserRequest request);

    void deleteAccount(Long id);
}
