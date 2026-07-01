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
import com.example.jobpilot.service.EducationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EducationServiceImpl implements EducationService {

    private final EducationRepository educationRepository;
    private final CvRepository cvRepository;
    private final EducationMapper educationMapper;

    public EducationServiceImpl(EducationRepository educationRepository,
                                CvRepository cvRepository,
                                EducationMapper educationMapper) {
        this.educationRepository = educationRepository;
        this.cvRepository = cvRepository;
        this.educationMapper = educationMapper;
    }

    @Override
    public EducationResponse create(Long cvId, CreateEducationRequest request) {

        Cv cv = cvRepository.findById(cvId)
                .orElseThrow(() -> new CvNotFoundException(cvId));

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

        return educationMapper.toResponse(education);
    }

    @Override
    public List<EducationResponse> getByCvId(Long cvId) {

        cvRepository.findById(cvId)
                .orElseThrow(() -> new CvNotFoundException(cvId));

        return educationRepository.findByCvId(cvId)
                .stream()
                .map(educationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public EducationResponse update(Long id, UpdateEducationRequest request) {

        Education education = educationRepository.findById(id)
                .orElseThrow(() -> new EducationNotFoundException(id));

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

        educationRepository.delete(education);
    }
}