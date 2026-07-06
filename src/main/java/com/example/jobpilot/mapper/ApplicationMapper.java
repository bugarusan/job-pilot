package com.example.jobpilot.mapper;

import com.example.jobpilot.dto.response.ApplicationResponse;
import com.example.jobpilot.entity.Application;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = JobMapper.class)
public interface ApplicationMapper {

    ApplicationResponse toResponse(Application application);
}