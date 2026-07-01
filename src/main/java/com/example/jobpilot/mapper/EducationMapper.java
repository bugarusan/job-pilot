package com.example.jobpilot.mapper;

import com.example.jobpilot.dto.response.EducationResponse;
import com.example.jobpilot.entity.Education;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EducationMapper {

    EducationResponse toResponse(Education education);
}
