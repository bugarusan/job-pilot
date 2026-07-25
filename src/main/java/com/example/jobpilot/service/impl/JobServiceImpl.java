package com.example.jobpilot.service.impl;

import com.example.jobpilot.dto.response.JobResponse;
import com.example.jobpilot.entity.Job;
import com.example.jobpilot.exception.JobNotFoundException;
import com.example.jobpilot.mapper.JobMapper;
import com.example.jobpilot.repository.JobRepository;
import com.example.jobpilot.service.JobService;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.List;

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
                .toList();
    }

    @Override
    public List<JobResponse> search(String query) {

        if (query == null || query.isBlank()) {
            return getAll();
        }

        query = query.trim();

        Set<Job> jobs = new LinkedHashSet<>();

        jobs.addAll(jobRepository.findByTitleContainingIgnoreCase(query));
        jobs.addAll(jobRepository.findByCompanyContainingIgnoreCase(query));
        jobs.addAll(jobRepository.findByLocationContainingIgnoreCase(query));
        jobs.addAll(jobRepository.findByDescriptionContainingIgnoreCase(query));
        jobs.addAll(jobRepository.findBySkill(query));

        return jobs.stream()
                .map(jobMapper::toResponse)
                .toList();
    }

    @Override
    public List<JobResponse> getBySkill(String skill) {

        return jobRepository.findBySkill(skill)
                .stream()
                .map(jobMapper::toResponse)
                .toList();
    }
}