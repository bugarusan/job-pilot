package com.example.jobpilot.service.impl;

import com.example.jobpilot.dto.request.CreateCvRequest;
import com.example.jobpilot.dto.request.UpdateCvRequest;
import com.example.jobpilot.dto.response.CvResponse;
import com.example.jobpilot.entity.Cv;
import com.example.jobpilot.entity.User;
import com.example.jobpilot.exception.CvNotFoundException;
import com.example.jobpilot.exception.UserNotFoundException;
import com.example.jobpilot.mapper.CvMapper;
import com.example.jobpilot.repository.CvRepository;
import com.example.jobpilot.repository.UserRepository;
import com.example.jobpilot.service.CvService;
import org.springframework.stereotype.Service;

@Service
public class CvServiceImpl implements CvService {

    private final CvRepository cvRepository;
    private final UserRepository userRepository;
    private final CvMapper cvMapper;

    public CvServiceImpl(CvRepository cvRepository,
                         UserRepository userRepository,
                         CvMapper cvMapper) {
        this.cvRepository = cvRepository;
        this.userRepository = userRepository;
        this.cvMapper = cvMapper;
    }

    @Override
    public CvResponse create(Long userId, CreateCvRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Cv cv = new Cv();
        cv.setFullName(request.getFullName());
        cv.setEmail(request.getEmail());
        cv.setPhone(request.getPhone());
        cv.setSummary(request.getSummary());
        cv.setUser(user);

        Cv saved = cvRepository.save(cv);

        return cvMapper.toResponse(saved);
    }

    @Override
    public CvResponse getById(Long id) {
        Cv cv = cvRepository.findById(id)
                .orElseThrow(() -> new CvNotFoundException(id));

        return cvMapper.toResponse(cv);
    }

    @Override
    public CvResponse getByUserId(Long userId) {
        Cv cv = cvRepository.findByUserId(userId)
                .orElseThrow(() -> new CvNotFoundException(userId));

        return cvMapper.toResponse(cv);
    }

    @Override
    public CvResponse update(Long id, UpdateCvRequest request) {

        Cv cv = cvRepository.findById(id)
                .orElseThrow(() -> new CvNotFoundException(id));

        if (request.getFullName() != null)
            cv.setFullName(request.getFullName());

        if (request.getEmail() != null)
            cv.setEmail(request.getEmail());

        if (request.getPhone() != null)
            cv.setPhone(request.getPhone());

        if (request.getSummary() != null)
            cv.setSummary(request.getSummary());

        Cv updated = cvRepository.save(cv);

        return cvMapper.toResponse(updated);
    }

    @Override
    public void delete(Long id) {
        cvRepository.deleteById(id);
    }
}