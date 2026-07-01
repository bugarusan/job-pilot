package com.example.jobpilot.mapper;

import com.example.jobpilot.dto.response.CvResponse;
import com.example.jobpilot.entity.Cv;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ExperienceMapper.class, EducationMapper.class, SkillMapper.class})
public interface CvMapper {

    CvResponse toResponse(Cv cv);

}