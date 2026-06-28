package com.example.jobpilot.service.impl;

import com.example.jobpilot.entity.Cv;
import com.example.jobpilot.entity.Experience;
import com.example.jobpilot.exception.CvNotFoundException;
import com.example.jobpilot.exception.ExperienceNotFoundException;
import com.example.jobpilot.repository.CvRepository;
import com.example.jobpilot.repository.ExperienceRepository;
import com.example.jobpilot.service.ExperienceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExperienceServiceImpl implements ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final CvRepository cvRepository;

    public ExperienceServiceImpl(ExperienceRepository experienceRepository, CvRepository cvRepository) {
        this.experienceRepository = experienceRepository;
        this.cvRepository = cvRepository;
    }


    @Override
    public Experience create(Long cvId, Experience experience) {
        Cv cv = cvRepository.findById(cvId)
                .orElseThrow(() -> new CvNotFoundException(cvId));

        experience.setCv(cv);

        return experienceRepository.save(experience);
    }

    @Override
    public Experience getById(Long id) {
        return experienceRepository.findById(id)
                .orElseThrow(() -> new ExperienceNotFoundException(id));
    }

    @Override
    public List<Experience> getByCvId(Long cvId) {

        cvRepository.findById(cvId)
                .orElseThrow(() -> new CvNotFoundException(cvId));

        return experienceRepository.findByCvId(cvId);
    }

    @Override
    public Experience update(Long id, Experience experience) {
        Experience existingExperience = experienceRepository.findById(id)
                .orElseThrow(() -> new ExperienceNotFoundException(id));
        existingExperience.setCompany(experience.getCompany());
        existingExperience.setDescription(experience.getDescription());
        existingExperience.setPosition(experience.getPosition());
        existingExperience.setEndDate(experience.getEndDate());
        existingExperience.setStartDate(experience.getStartDate());


        return experienceRepository.save(existingExperience);
    }

    @Override
    public void delete(Long id) {
        Experience experience = experienceRepository.findById(id)
                .orElseThrow(() -> new ExperienceNotFoundException(id));

        experienceRepository.delete(experience);


    }
}
