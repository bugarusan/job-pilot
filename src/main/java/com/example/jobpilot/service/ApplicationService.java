package com.example.jobpilot.service;

import com.example.jobpilot.entity.Application;
import com.example.jobpilot.enums.ApplicationStatus;

import java.util.List;

public interface ApplicationService {

    Application create(Long userId, Long jobId);

    Application getById(Long id);

    List<Application> getByUserId(Long userId);

    Application updateStatus(Long id, ApplicationStatus status);

    void delete(Long id);
}
