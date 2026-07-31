package com.example.jobpilot.scheduler;

import com.example.jobpilot.service.GaijinPotImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GaijinPotScheduler {

    private final GaijinPotImportService gaijinPotImportService;

    @Scheduled(cron = "0 0 */2 * * *")
    public void importJobs() {
        gaijinPotImportService.importJobs();
    }
}
