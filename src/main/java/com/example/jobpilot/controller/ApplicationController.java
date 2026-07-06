package com.example.jobpilot.controller;

import com.example.jobpilot.dto.request.UpdateApplicationStatusRequest;
import com.example.jobpilot.dto.response.ApplicationResponse;
import com.example.jobpilot.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping("/users/{userId}/jobs/{jobId}/applications")
    public ResponseEntity<ApplicationResponse> create(@PathVariable Long userId, @PathVariable Long jobId) {
        ApplicationResponse response = applicationService.create(userId, jobId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/applications/{id}")
    public ResponseEntity<ApplicationResponse> getById(@PathVariable Long id) {
        ApplicationResponse response = applicationService.getById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{userId}/applications")
    public ResponseEntity<List<ApplicationResponse>> getByUserId(@PathVariable Long userId) {
        List<ApplicationResponse> response = applicationService.getByUserId(userId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/applications/{id}/status")
    public ResponseEntity<ApplicationResponse> updateStatus(@PathVariable Long id, @Valid @RequestBody UpdateApplicationStatusRequest request) {
     ApplicationResponse response=applicationService.updateStatus(id, request);
     return ResponseEntity.ok(response);
    }

    @DeleteMapping("/applications/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        applicationService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
