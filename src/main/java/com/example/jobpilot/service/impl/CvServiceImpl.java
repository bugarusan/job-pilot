package com.example.jobpilot.service.impl;

import com.example.jobpilot.entity.Cv;
import com.example.jobpilot.entity.User;
import com.example.jobpilot.exception.CvAlreadyExistsException;
import com.example.jobpilot.exception.CvNotFoundException;
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
        return cvRepository.findById(id)
                .orElseThrow(() -> new CvNotFoundException(id));
    }

    @Override
    public Cv getByUserId(Long userId) {
        return cvRepository.findByUserId(userId)
                .orElseThrow(() -> new CvNotFoundException(userId));
    }

    @Override
    public Cv update(Long id, Cv cv) {

        Cv existingCv = cvRepository.findById(id)
                .orElseThrow(() -> new CvNotFoundException(id));

        existingCv.setFullName(cv.getFullName());
        existingCv.setEmail(cv.getEmail());
        existingCv.setPhone(cv.getPhone());
        existingCv.setSummary(cv.getSummary());

        return cvRepository.save(existingCv);
    }

    @Override
    public void delete(Long id) {

        Cv cv = cvRepository.findById(id)
                .orElseThrow(() -> new CvNotFoundException(id));

        cvRepository.delete(cv);
    }
}
