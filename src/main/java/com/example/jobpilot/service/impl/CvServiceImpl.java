package com.example.jobpilot.service.impl;

import com.example.jobpilot.entity.Cv;
import com.example.jobpilot.entity.User;
import com.example.jobpilot.exception.CvAlreadyExistsException;
import com.example.jobpilot.exception.UserNotFoundException;
import com.example.jobpilot.repository.CvRepository;
import com.example.jobpilot.repository.UserRepository;
import com.example.jobpilot.service.CvService;
import org.springframework.stereotype.Service;

@Service
public class CvServiceImpl implements CvService {

    private final CvRepository cvRepository;
    private final UserRepository userRepository;

    public CvServiceImpl(CvRepository cvRepository, UserRepository userRepository) {
        this.cvRepository = cvRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Cv create(Long userId, Cv cv) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        if (cvRepository.findByUserId(userId).isPresent()) {
            throw new CvAlreadyExistsException(userId);
        }
        cv.setUser(user);

        return cvRepository.save(cv);
    }

    @Override
    public Cv getById(Long id) {
        return null;
    }

    @Override
    public Cv getByUserId(Long userId) {
        return null;
    }

    @Override
    public Cv update(Long id, Cv cv) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
