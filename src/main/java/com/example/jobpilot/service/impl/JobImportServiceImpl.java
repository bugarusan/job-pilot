package com.example.jobpilot.service.impl;

import com.example.jobpilot.dto.api.ArbeitnowJobDto;
import com.example.jobpilot.dto.api.ArbeitnowResponseDto;
import com.example.jobpilot.entity.Job;
import com.example.jobpilot.repository.JobRepository;
import com.example.jobpilot.service.JobImportService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JobImportServiceImpl implements JobImportService {

    private final RestClient restClient;
    private final JobRepository jobRepository;

    public JobImportServiceImpl(RestClient restClient,
                                JobRepository jobRepository) {
        this.restClient = restClient;
        this.jobRepository = jobRepository;
    }

    @Override
    public void importJobs() {

        ArbeitnowResponseDto response = restClient.get()
                .uri("https://www.arbeitnow.com/api/job-board-api")
                .retrieve()
                .body(ArbeitnowResponseDto.class);

        if (response == null || response.getData() == null) {
            return;
        }

        for (ArbeitnowJobDto dto : response.getData()) {

            if (jobRepository.existsByUrl(dto.getUrl())) {
                continue;
            }

            Job job = new Job();

            job.setTitle(dto.getTitle());
            job.setCompany(dto.getCompany_name());
            job.setDescription(dto.getDescription());
            job.setLocation(dto.getLocation());
            job.setRemote(dto.isRemote());
            job.setUrl(dto.getUrl());
            job.setRequiredSkills(dto.getTags());

            if (dto.getCreated_at() != null) {
                job.setCreatedAt(
                        LocalDateTime.ofInstant(
                                Instant.ofEpochSecond(dto.getCreated_at()),
                                ZoneOffset.UTC
                        )
                );
            }

            jobRepository.save(job);
        }
    }
}