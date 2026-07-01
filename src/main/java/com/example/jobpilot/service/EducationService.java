package com.example.jobpilot.service;

import com.example.jobpilot.dto.request.CreateEducationRequest;
import com.example.jobpilot.dto.request.UpdateEducationRequest;
import com.example.jobpilot.dto.response.EducationResponse;

import java.util.List;

public interface EducationService {

    EducationResponse create(Long cvId, CreateEducationRequest request);

    EducationResponse getById(Long id);

    List<EducationResponse> getByCvId(Long cvId);

    EducationResponse update(Long id, UpdateEducationRequest request);

    void delete(Long id);
}