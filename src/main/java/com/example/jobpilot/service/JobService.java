package com.example.jobpilot.service;

import com.example.jobpilot.dto.response.JobResponse;

import java.util.List;

public interface JobService {

    JobResponse getById(Long id);

    List<JobResponse> getAll();
}