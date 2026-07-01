package com.example.jobpilot.service;

import com.example.jobpilot.dto.request.CreateSkillRequest;
import com.example.jobpilot.dto.request.UpdateSkillRequest;
import com.example.jobpilot.dto.response.SkillResponse;

import java.util.List;

public interface SkillService {

    SkillResponse create(Long cvId, CreateSkillRequest request);

    SkillResponse getById(Long id);

    List<SkillResponse> getByCvId(Long cvId);

    SkillResponse update(Long id, UpdateSkillRequest request);

    void delete(Long id);
}