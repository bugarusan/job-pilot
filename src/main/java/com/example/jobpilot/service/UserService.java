package com.example.jobpilot.service;

import com.example.jobpilot.entity.User;

public interface UserService {
    User register(User user);

    User getById(Long id);

    User getByEmail(String email);

    User updateProfile(Long id, User user);

    void deleteAccount(Long id);
}
