package com.example.jobpilot.mapper;

import com.example.jobpilot.dto.response.ExperienceResponse;
import com.example.jobpilot.entity.Experience;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExperienceMapper {

    ExperienceResponse toResponse(Experience experience);
}
