package com.example.jobpilot.service;

import com.example.jobpilot.entity.Cv;

public interface CvService {
    Cv create(Long userId, Cv cv);

    Cv getById(Long id);

    Cv getByUserId(Long userId);

    Cv update(Long id, Cv cv);

    void delete(Long id);
}
