package com.example.jobpilot.service.impl;

import com.example.jobpilot.entity.Application;
import com.example.jobpilot.entity.Job;
import com.example.jobpilot.entity.User;
import com.example.jobpilot.enums.ApplicationStatus;
import com.example.jobpilot.exception.ApplicationAlreadyExistsException;
import com.example.jobpilot.exception.ApplicationNotFoundException;
import com.example.jobpilot.exception.JobNotFoundException;
import com.example.jobpilot.exception.UserNotFoundException;
import com.example.jobpilot.repository.ApplicationRepository;
import com.example.jobpilot.repository.JobRepository;
import com.example.jobpilot.repository.UserRepository;
import com.example.jobpilot.service.ApplicationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository,
                                  UserRepository userRepository,
                                  JobRepository jobRepository) {
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
    }

    @Override
    public Application create(Long userId, Long jobId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new JobNotFoundException(jobId));

        applicationRepository.findByUserIdAndJobId(userId, jobId)
                .ifPresent(application -> {
                    throw new ApplicationAlreadyExistsException(userId, jobId);
                });

        Application application = new Application();

        application.setUser(user);
        application.setJob(job);
        application.setStatus(ApplicationStatus.APPLIED);

        return applicationRepository.save(application);
    }

    @Override
    public Application getById(Long id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException(id));
    }

    @Override
    public List<Application> getByUserId(Long userId) {

        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        return applicationRepository.findByUserId(userId);
    }

    @Override
    public Application updateStatus(Long id, ApplicationStatus status) {

        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException(id));

        application.setStatus(status);

        return applicationRepository.save(application);
    }

    @Override
    public void delete(Long id) {

        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException(id));

        applicationRepository.delete(application);
    }
}