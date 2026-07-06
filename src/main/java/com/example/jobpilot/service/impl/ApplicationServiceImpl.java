package com.example.jobpilot.service.impl;

import com.example.jobpilot.dto.request.UpdateApplicationStatusRequest;
import com.example.jobpilot.dto.response.ApplicationResponse;
import com.example.jobpilot.entity.Application;
import com.example.jobpilot.entity.Job;
import com.example.jobpilot.entity.User;
import com.example.jobpilot.enums.ApplicationStatus;
import com.example.jobpilot.exception.ApplicationAlreadyExistsException;
import com.example.jobpilot.exception.ApplicationNotFoundException;
import com.example.jobpilot.exception.JobNotFoundException;
import com.example.jobpilot.exception.UserNotFoundException;
import com.example.jobpilot.mapper.ApplicationMapper;
import com.example.jobpilot.repository.ApplicationRepository;
import com.example.jobpilot.repository.JobRepository;
import com.example.jobpilot.repository.UserRepository;
import com.example.jobpilot.service.ApplicationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final ApplicationMapper applicationMapper;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository,
                                  UserRepository userRepository,
                                  JobRepository jobRepository,
                                  ApplicationMapper applicationMapper) {
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
        this.applicationMapper = applicationMapper;
    }

    @Override
    public ApplicationResponse create(Long userId, Long jobId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new JobNotFoundException(jobId));

        applicationRepository.findByUserIdAndJobId(userId, jobId)
                .ifPresent(app -> {
                    throw new ApplicationAlreadyExistsException(userId, jobId);
                });

        Application application = new Application();
        application.setUser(user);
        application.setJob(job);
        application.setStatus(ApplicationStatus.APPLIED);

        Application saved = applicationRepository.save(application);

        return applicationMapper.toResponse(saved);
    }

    @Override
    public ApplicationResponse getById(Long id) {

        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException(id));

        return applicationMapper.toResponse(application);
    }

    @Override
    public List<ApplicationResponse> getByUserId(Long userId) {

        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        return applicationRepository.findByUserId(userId)
                .stream()
                .map(applicationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ApplicationResponse updateStatus(Long id, UpdateApplicationStatusRequest request) {

        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException(id));

        application.setStatus(request.getStatus());

        Application updated = applicationRepository.save(application);

        return applicationMapper.toResponse(updated);
    }

    @Override
    public void delete(Long id) {

        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException(id));

        applicationRepository.delete(application);
    }
}