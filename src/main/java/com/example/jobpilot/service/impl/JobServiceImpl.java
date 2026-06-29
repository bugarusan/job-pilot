package com.example.jobpilot.service.impl;

import com.example.jobpilot.entity.Job;
import com.example.jobpilot.exception.ExperienceNotFoundException;
import com.example.jobpilot.exception.JobNotFoundException;
import com.example.jobpilot.repository.JobRepository;
import com.example.jobpilot.service.JobService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public Job getById(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new JobNotFoundException(id));
    }

    @Override
    public List<Job> getAll() {
        return jobRepository.findAll();
    }
}
