package com.example.jobpilot.service.impl;

import com.example.jobpilot.dto.request.CreateEducationRequest;
import com.example.jobpilot.dto.request.UpdateEducationRequest;
import com.example.jobpilot.dto.response.EducationResponse;
import com.example.jobpilot.entity.Cv;
import com.example.jobpilot.entity.Education;
import com.example.jobpilot.exception.CvNotFoundException;
import com.example.jobpilot.exception.EducationNotFoundException;
import com.example.jobpilot.mapper.EducationMapper;
import com.example.jobpilot.repository.CvRepository;
import com.example.jobpilot.repository.EducationRepository;
import com.example.jobpilot.security.CurrentUserProvider;
import com.example.jobpilot.service.EducationService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EducationServiceImpl implements EducationService {

    private final EducationRepository educationRepository;
    private final CvRepository cvRepository;
    private final EducationMapper educationMapper;
    private final CurrentUserProvider currentUserProvider;

    public EducationServiceImpl(EducationRepository educationRepository,
                                CvRepository cvRepository,
                                EducationMapper educationMapper,
                                CurrentUserProvider currentUserProvider) {
        this.educationRepository = educationRepository;
        this.cvRepository = cvRepository;
        this.educationMapper = educationMapper;
        this.currentUserProvider = currentUserProvider;
    }

    private void checkOwnership(Cv cv) {
        Long currentUserId = currentUserProvider.getCurrentUserId();
        if (!currentUserId.equals(cv.getUser().getId())) {
            throw new AccessDeniedException("Bu CV sənə aid deyil");
        }
    }

    @Override
    public EducationResponse create(Long cvId, CreateEducationRequest request) {

        Cv cv = cvRepository.findById(cvId)
                .orElseThrow(() -> new CvNotFoundException(cvId));

        checkOwnership(cv);

        Education education = new Education();
        education.setSchool(request.getSchool());
        education.setDegree(request.getDegree());
        education.setField(request.getField());
        education.setStartDate(request.getStartDate());
        education.setEndDate(request.getEndDate());
        education.setCv(cv);

        Education saved = educationRepository.save(education);

        return educationMapper.toResponse(saved);
    }

    @Override
    public EducationResponse getById(Long id) {

        Education education = educationRepository.findById(id)
                .orElseThrow(() -> new EducationNotFoundException(id));

        checkOwnership(education.getCv());

        return educationMapper.toResponse(education);
    }

    @Override
    public List<EducationResponse> getByCvId(Long cvId) {

        Cv cv = cvRepository.findById(cvId)
                .orElseThrow(() -> new CvNotFoundException(cvId));

        checkOwnership(cv);

        return educationRepository.findByCvId(cvId)
                .stream()
                .map(educationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public EducationResponse update(Long id, UpdateEducationRequest request) {

        Education education = educationRepository.findById(id)
                .orElseThrow(() -> new EducationNotFoundException(id));

        checkOwnership(education.getCv());

        if (request.getSchool() != null)
            education.setSchool(request.getSchool());

        if (request.getDegree() != null)
            education.setDegree(request.getDegree());

        if (request.getField() != null)
            education.setField(request.getField());

        if (request.getStartDate() != null)
            education.setStartDate(request.getStartDate());

        if (request.getEndDate() != null)
            education.setEndDate(request.getEndDate());

        Education updated = educationRepository.save(education);

        return educationMapper.toResponse(updated);
    }

    @Override
    public void delete(Long id) {

        Education education = educationRepository.findById(id)
                .orElseThrow(() -> new EducationNotFoundException(id));

        checkOwnership(education.getCv());

        educationRepository.delete(education);
    }
}