package com.example.jobpilot.mapper;

import com.example.jobpilot.dto.response.JobResponse;
import com.example.jobpilot.entity.Job;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JobMapper {

    JobResponse toResponse(Job job);
}