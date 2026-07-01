package com.example.jobpilot.mapper;

import com.example.jobpilot.dto.response.SkillResponse;
import com.example.jobpilot.entity.Skill;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SkillMapper {

    SkillResponse toResponse(Skill skill);
}
