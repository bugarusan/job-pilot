package com.example.jobpilot.scheduler;

import com.example.jobpilot.service.JobsInJapanImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobsInJapanScheduler {

    private final JobsInJapanImportService jobsInJapanImportService;

    @Scheduled(cron = "0 0 */2 * * *")
    public void importJobs() {
        jobsInJapanImportService.importJobs();
    }
}