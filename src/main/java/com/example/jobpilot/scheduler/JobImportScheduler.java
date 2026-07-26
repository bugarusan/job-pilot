package com.example.jobpilot.scheduler;

import com.example.jobpilot.service.JobImportService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class JobImportScheduler {

    private final JobImportService jobImportService;

    public JobImportScheduler(JobImportService jobImportService) {
        this.jobImportService = jobImportService;
    }

    @Scheduled(fixedRate = 900000)
    public void importJobs() {
        jobImportService.importJobs();
    }
}