package com.example.jobpilot.service.impl;

import com.example.jobpilot.dto.request.RegisterRequest;
import com.example.jobpilot.dto.request.UpdateUserRequest;
import com.example.jobpilot.dto.response.UserResponse;
import com.example.jobpilot.entity.User;
import com.example.jobpilot.exception.EmailAlreadyExistsException;
import com.example.jobpilot.exception.UserNotFoundException;
import com.example.jobpilot.mapper.UserMapper;
import com.example.jobpilot.repository.UserRepository;
import com.example.jobpilot.security.CurrentUserProvider;
import com.example.jobpilot.service.UserService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CurrentUserProvider currentUserProvider;

    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper,
                           PasswordEncoder passwordEncoder,
                           CurrentUserProvider currentUserProvider) {

        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.currentUserProvider = currentUserProvider;
    }

    private void checkOwnership(Long targetUserId) {
        Long currentUserId = currentUserProvider.getCurrentUserId();
        if (!currentUserId.equals(targetUserId)) {
            throw new AccessDeniedException("Bu hesab sənə aid deyil");
        }
    }

    @Override
    public UserResponse register(RegisterRequest request) {

        userRepository.findByEmail(request.getEmail())
                .ifPresent(user -> {
                    throw new EmailAlreadyExistsException(request.getEmail());
                });

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User saved = userRepository.save(user);

        return userMapper.toResponse(saved);
    }

    @Override
    public UserResponse getById(Long id) {

        checkOwnership(id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse getByEmail(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse updateProfile(Long id, UpdateUserRequest request) {

        checkOwnership(id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (request.getFullName() != null) {
            user.setFullName(request.getFullName());
        }

        if (request.getLocation() != null) {
            user.setLocation(request.getLocation());
        }

        User updated = userRepository.save(user);

        return userMapper.toResponse(updated);
    }

    @Override
    public void deleteAccount(Long id) {

        checkOwnership(id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        userRepository.delete(user);
    }
}