package com.example.jobpilot.service;

import com.example.jobpilot.dto.request.CreateCvRequest;
import com.example.jobpilot.dto.request.UpdateCvRequest;
import com.example.jobpilot.dto.response.CvResponse;

public interface CvService {

    CvResponse create(Long userId, CreateCvRequest request);

    CvResponse getById(Long id);

    CvResponse getByUserId(Long userId);

    CvResponse update(Long id, UpdateCvRequest request);

    void delete(Long id);
}