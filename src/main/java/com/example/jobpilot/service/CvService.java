package com.example.jobpilot.service;

import com.example.jobpilot.dto.request.CreateCvRequest;
import com.example.jobpilot.dto.request.UpdateCvRequest;
import com.example.jobpilot.dto.response.CvResponse;

import java.util.List;

public interface CvService {

    CvResponse create(Long userId, CreateCvRequest request);

    CvResponse getById(Long id);

    List<CvResponse> getByUserId(Long userId);

    CvResponse update(Long id, UpdateCvRequest request);

    CvResponse setDefault(Long cvId);

    void delete(Long id);
}