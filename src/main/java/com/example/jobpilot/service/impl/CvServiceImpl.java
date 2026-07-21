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
import com.example.jobpilot.security.CurrentUserProvider;
import com.example.jobpilot.service.CvService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

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
            throw new AccessDeniedException("This CV is not about you");
        }
    }

    @Override
    public CvResponse create(Long userId, CreateCvRequest request) {

        checkOwnership(userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Cv cv = new Cv();
        cv.setFullName(request.getFullName());
        cv.setEmail(request.getEmail());
        cv.setPhone(request.getPhone());
        cv.setSummary(request.getSummary());
        cv.setTitle(request.getTitle());

        boolean hasDefaultCv =
                cvRepository.findByUserIdAndIsDefaultTrue(userId).isPresent();

        cv.setDefault(!hasDefaultCv);

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
    public List<CvResponse> getByUserId(Long userId) {

        checkOwnership(userId);

        List<Cv> cvs = cvRepository.findByUserId(userId);

        if (cvs.isEmpty()) {
            throw new CvNotFoundException(userId);
        }

        return cvs.stream()
                .map(cvMapper::toResponse)
                .toList();
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

        if (request.getTitle() != null)
            cv.setTitle(request.getTitle());

        Cv updated = cvRepository.save(cv);

        return cvMapper.toResponse(updated);
    }

    @Override
    public CvResponse setDefault(Long cvId) {

        Cv selectedCv = cvRepository.findById(cvId)
                .orElseThrow(() -> new CvNotFoundException(cvId));

        checkOwnership(selectedCv.getUser().getId());

        if (selectedCv.isDefault()) {
            return cvMapper.toResponse(selectedCv);
        }

        Long userId = selectedCv.getUser().getId();

        cvRepository.findByUserIdAndIsDefaultTrue(userId)
                .ifPresent(currentDefault -> {
                    currentDefault.setDefault(false);
                    cvRepository.save(currentDefault);
                });

        selectedCv.setDefault(true);

        Cv updated = cvRepository.save(selectedCv);

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