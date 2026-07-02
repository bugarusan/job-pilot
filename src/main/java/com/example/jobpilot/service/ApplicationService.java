package com.example.jobpilot.service;

import com.example.jobpilot.dto.response.ApplicationResponse;
import com.example.jobpilot.enums.ApplicationStatus;

import java.util.List;

public interface ApplicationService {

    ApplicationResponse create(Long userId, Long jobId);

    ApplicationResponse getById(Long id);

    List<ApplicationResponse> getByUserId(Long userId);

    ApplicationResponse updateStatus(Long id, ApplicationStatus status);

    void delete(Long id);
}