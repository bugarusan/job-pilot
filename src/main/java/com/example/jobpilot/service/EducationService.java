package com.example.jobpilot.service;

import com.example.jobpilot.entity.Education;

import java.util.List;

public interface EducationService {
    Education create(Long cvId,Education education);

    Education getById(Long id);

    List<Education> getByCvId(Long cvId);

    Education update(Long id,Education education);

    void delete(Long id);
}
