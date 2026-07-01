package com.example.jobpilot.service;

import com.example.jobpilot.dto.request.CreateExperienceRequest;
import com.example.jobpilot.dto.request.UpdateExperienceRequest;
import com.example.jobpilot.dto.response.ExperienceResponse;
import com.example.jobpilot.entity.Experience;

import java.util.List;

public interface ExperienceService {

    ExperienceResponse create(Long cvId, CreateExperienceRequest request);

    ExperienceResponse getById(Long id);

    List<ExperienceResponse> getByCvId(Long cvId);

    ExperienceResponse update(Long id, UpdateExperienceRequest request);

    void delete(Long id);
}
