package com.example.jobpilot.controller;

import com.example.jobpilot.dto.request.CreateExperienceRequest;
import com.example.jobpilot.dto.request.UpdateExperienceRequest;
import com.example.jobpilot.dto.response.ExperienceResponse;
import com.example.jobpilot.service.ExperienceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cvs")
@RequiredArgsConstructor
public class ExperienceController {

    private final ExperienceService experienceService;

    @PostMapping("/{cvId}/experiences")
    public ResponseEntity<ExperienceResponse> create(@PathVariable Long cvId, @Valid @RequestBody CreateExperienceRequest request) {
        ExperienceResponse response = experienceService.create(cvId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/experiences/{id}")
    public ResponseEntity<ExperienceResponse> getById(@PathVariable Long id) {
        ExperienceResponse response = experienceService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{cvId}/experiences")
    public ResponseEntity<List<ExperienceResponse>> getByCvId(@PathVariable Long cvId) {

        List<ExperienceResponse> response = experienceService.getByCvId(cvId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/experiences/{id}")
    public ResponseEntity<ExperienceResponse> update(@PathVariable Long id, @Valid @RequestBody UpdateExperienceRequest request) {
    ExperienceResponse response=experienceService.update(id, request);
    return ResponseEntity.ok(response);

    }

    @DeleteMapping("/experiences/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        experienceService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
