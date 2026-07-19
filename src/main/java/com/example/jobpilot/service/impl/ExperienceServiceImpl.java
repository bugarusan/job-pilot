package com.example.jobpilot.service.impl;

import com.example.jobpilot.dto.request.CreateExperienceRequest;
import com.example.jobpilot.dto.request.UpdateExperienceRequest;
import com.example.jobpilot.dto.response.ExperienceResponse;
import com.example.jobpilot.entity.Cv;
import com.example.jobpilot.entity.Experience;
import com.example.jobpilot.exception.CvNotFoundException;
import com.example.jobpilot.exception.ExperienceNotFoundException;
import com.example.jobpilot.mapper.ExperienceMapper;
import com.example.jobpilot.repository.CvRepository;
import com.example.jobpilot.repository.ExperienceRepository;
import com.example.jobpilot.security.CurrentUserProvider;
import com.example.jobpilot.service.ExperienceService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExperienceServiceImpl implements ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final CvRepository cvRepository;
    private final ExperienceMapper experienceMapper;
    private final CurrentUserProvider currentUserProvider;

    public ExperienceServiceImpl(ExperienceRepository experienceRepository,
                                 CvRepository cvRepository,
                                 ExperienceMapper experienceMapper,
                                 CurrentUserProvider currentUserProvider) {
        this.experienceRepository = experienceRepository;
        this.cvRepository = cvRepository;
        this.experienceMapper = experienceMapper;
        this.currentUserProvider = currentUserProvider;
    }

    private void checkOwnership(Cv cv) {
        Long currentUserId = currentUserProvider.getCurrentUserId();
        if (!currentUserId.equals(cv.getUser().getId())) {
            throw new AccessDeniedException("Bu CV sənə aid deyil");
        }
    }

    @Override
    public ExperienceResponse create(Long cvId, CreateExperienceRequest request) {

        Cv cv = cvRepository.findById(cvId)
                .orElseThrow(() -> new CvNotFoundException(cvId));

        checkOwnership(cv);

        Experience experience = new Experience();
        experience.setCompany(request.getCompany());
        experience.setPosition(request.getPosition());
        experience.setStartDate(request.getStartDate());
        experience.setEndDate(request.getEndDate());
        experience.setDescription(request.getDescription());
        experience.setCv(cv);

        Experience saved = experienceRepository.save(experience);

        return experienceMapper.toResponse(saved);
    }

    @Override
    public ExperienceResponse getById(Long id) {

        Experience experience = experienceRepository.findById(id)
                .orElseThrow(() -> new ExperienceNotFoundException(id));

        checkOwnership(experience.getCv());

        return experienceMapper.toResponse(experience);
    }

    @Override
    public List<ExperienceResponse> getByCvId(Long cvId) {

        Cv cv = cvRepository.findById(cvId)
                .orElseThrow(() -> new CvNotFoundException(cvId));

        checkOwnership(cv);

        return experienceRepository.findByCvId(cvId)
                .stream()
                .map(experienceMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ExperienceResponse update(Long id, UpdateExperienceRequest request) {

        Experience experience = experienceRepository.findById(id)
                .orElseThrow(() -> new ExperienceNotFoundException(id));

        checkOwnership(experience.getCv());

        if (request.getCompany() != null)
            experience.setCompany(request.getCompany());

        if (request.getPosition() != null)
            experience.setPosition(request.getPosition());

        if (request.getStartDate() != null)
            experience.setStartDate(request.getStartDate());

        if (request.getEndDate() != null)
            experience.setEndDate(request.getEndDate());

        if (request.getDescription() != null)
            experience.setDescription(request.getDescription());

        Experience updated = experienceRepository.save(experience);

        return experienceMapper.toResponse(updated);
    }

    @Override
    public void delete(Long id) {

        Experience experience = experienceRepository.findById(id)
                .orElseThrow(() -> new ExperienceNotFoundException(id));

        checkOwnership(experience.getCv());

        experienceRepository.delete(experience);
    }
}