package com.example.jobpilot.service.impl;

import com.example.jobpilot.entity.Cv;
import com.example.jobpilot.entity.Education;
import com.example.jobpilot.exception.CvNotFoundException;
import com.example.jobpilot.exception.EducationNotFoundException;
import com.example.jobpilot.repository.CvRepository;
import com.example.jobpilot.repository.EducationRepository;
import com.example.jobpilot.service.EducationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EducationServiceImpl implements EducationService {
    private final EducationRepository educationRepository;
    private final CvRepository cvRepository;

    public EducationServiceImpl(EducationRepository educationRepository, CvRepository cvRepository) {
        this.educationRepository = educationRepository;
        this.cvRepository = cvRepository;
    }

    @Override
    public Education create(Long cvId, Education education) {
        Cv cv = cvRepository.findById(cvId)
                .orElseThrow(() -> new CvNotFoundException(cvId));

        education.setCv(cv);
        return educationRepository.save(education);
    }

    @Override
    public Education getById(Long id) {
        return educationRepository.findById(id)
                .orElseThrow(() -> new EducationNotFoundException(id));
    }

    @Override
    public List<Education> getByCvId(Long cvId) {
        cvRepository.findById(cvId)
                .orElseThrow(() -> new CvNotFoundException(cvId));

        return educationRepository.findByCvId(cvId);
    }

    @Override
    public Education update(Long id, Education education) {
        Education existingEducation = educationRepository.findById(id)
                .orElseThrow(() -> new EducationNotFoundException(id));
        existingEducation.setSchool(education.getSchool());
        existingEducation.setDegree(education.getDegree());
        existingEducation.setField(education.getField());
        existingEducation.setStartDate(education.getStartDate());
        existingEducation.setEndDate(education.getEndDate());

        return educationRepository.save(existingEducation);

    }

    @Override
    public void delete(Long id) {
        Education education = educationRepository.findById(id)
                .orElseThrow(() -> new EducationNotFoundException(id));

        educationRepository.delete(education);

    }
}
