package com.example.jobpilot.mapper;

import com.example.jobpilot.dto.response.ApplicationResponse;
import com.example.jobpilot.entity.Application;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {JobMapper.class})
public interface ApplicationMapper {

    @Mapping(source = "job", target = "jobResponse")
    ApplicationResponse toResponse(Application application);
}