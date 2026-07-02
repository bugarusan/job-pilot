package com.example.jobpilot.mapper;

import com.example.jobpilot.dto.response.UserResponse;
import com.example.jobpilot.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toResponse(User user);
}