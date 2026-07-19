package com.example.jobpilot.service;

import com.example.jobpilot.dto.request.LoginRequest;
import com.example.jobpilot.dto.response.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
}
