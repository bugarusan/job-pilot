package com.example.jobpilot.service.impl;

import com.example.jobpilot.dto.request.CreateCvRequest;
import com.example.jobpilot.dto.request.UpdateCvRequest;
import com.example.jobpilot.dto.response.CvResponse;
import com.example.jobpilot.entity.Cv;
import com.example.jobpilot.entity.User;
import com.example.jobpilot.exception.CvAlreadyExistsException;
import com.example.jobpilot.exception.CvNotFoundException;
import com.example.jobpilot.exception.UserNotFoundException;
import com.example.jobpilot.mapper.CvMapper;
import com.example.jobpilot.repository.CvRepository;
import com.example.jobpilot.repository.UserRepository;
import com.example.jobpilot.security.CurrentUserProvider;
import com.example.jobpilot.service.CvService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class CvServiceImpl implements CvService {

    private final CvRepository cvRepository;
    private final UserRepository userRepository;
    private final CvMapper cvMapper;
    private final CurrentUserProvider currentUserProvider;

    public CvServiceImpl(CvRepository cvRepository,
                         UserRepository userRepository,
                         CvMapper cvMapper,
                         CurrentUserProvider currentUserProvider) {
        this.cvRepository = cvRepository;
        this.userRepository = userRepository;
        this.cvMapper = cvMapper;
        this.currentUserProvider = currentUserProvider;
    }

    private void checkOwnership(Long targetUserId) {
        Long currentUserId = currentUserProvider.getCurrentUserId();
        if (!currentUserId.equals(targetUserId)) {
            throw new AccessDeniedException("Bu CV sənə aid deyil");
        }
    }

    @Override
    public CvResponse create(Long userId, CreateCvRequest request) {

        checkOwnership(userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        cvRepository.findByUserId(userId)
                .ifPresent(existingCv -> {
                    throw new CvAlreadyExistsException(userId);
                });

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

        checkOwnership(cv.getUser().getId());

        return cvMapper.toResponse(cv);
    }

    @Override
    public CvResponse getByUserId(Long userId) {

        checkOwnership(userId);

        Cv cv = cvRepository.findByUserId(userId)
                .orElseThrow(() -> new CvNotFoundException(userId));

        return cvMapper.toResponse(cv);
    }

    @Override
    public CvResponse update(Long id, UpdateCvRequest request) {

        Cv cv = cvRepository.findById(id)
                .orElseThrow(() -> new CvNotFoundException(id));

        checkOwnership(cv.getUser().getId());

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

        Cv cv = cvRepository.findById(id)
                .orElseThrow(() -> new CvNotFoundException(id));

        checkOwnership(cv.getUser().getId());

        cvRepository.delete(cv);
    }
}