package com.example.jobpilot.service;

import com.example.jobpilot.entity.Experience;

import java.util.List;

public interface ExperienceService {

    Experience create(Long cvId, Experience experience);

    Experience getById(Long id);

    List<Experience> getByCvId(Long cvId);

    Experience update(Long id, Experience experience);

    void delete(Long id);
}
