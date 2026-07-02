package com.example.jobpilot.service.impl;

import com.example.jobpilot.dto.response.JobResponse;
import com.example.jobpilot.entity.Job;
import com.example.jobpilot.exception.JobNotFoundException;
import com.example.jobpilot.mapper.JobMapper;
import com.example.jobpilot.repository.JobRepository;
import com.example.jobpilot.service.JobService;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final JobMapper jobMapper;

    public JobServiceImpl(JobRepository jobRepository,
                          JobMapper jobMapper) {
        this.jobRepository = jobRepository;
        this.jobMapper = jobMapper;
    }

    @Override
    public JobResponse getById(Long id) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new JobNotFoundException(id));

        return jobMapper.toResponse(job);
    }

    @Override
    public List<JobResponse> getAll() {

        return jobRepository.findAll()
                .stream()
                .map(jobMapper::toResponse)
                .collect(Collectors.toList());
    }
}