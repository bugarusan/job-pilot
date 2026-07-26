package com.example.jobpilot.controller;

import com.example.jobpilot.dto.response.JobResponse;
import com.example.jobpilot.service.JobImportService;
import com.example.jobpilot.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;
    private final JobImportService jobImportService;

    @GetMapping("/all")
    public ResponseEntity<List<JobResponse>> getAll() {

        List<JobResponse> response = jobService.getAll();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobResponse> getById(@PathVariable Long id) {

        JobResponse response = jobService.getById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<JobResponse>> search(
            @RequestParam String query) {

        List<JobResponse> response = jobService.search(query);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/skills")
    public ResponseEntity<List<JobResponse>> getBySkill(
            @RequestParam String skill) {

        List<JobResponse> response = jobService.getBySkill(skill);

        return ResponseEntity.ok(response);
    }

}