package com.example.jobpilot.controller;

import com.example.jobpilot.dto.request.CreateEducationRequest;
import com.example.jobpilot.dto.request.UpdateEducationRequest;
import com.example.jobpilot.dto.response.EducationResponse;
import com.example.jobpilot.service.EducationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cvs")
@RequiredArgsConstructor
public class EducationController {

    private final EducationService educationService;

    @PostMapping("/{cvId}/educations")
    public ResponseEntity<EducationResponse> create(@PathVariable Long cvId, @Valid @RequestBody CreateEducationRequest request) {
        EducationResponse response = educationService.create(cvId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/educations/{id}")
    public ResponseEntity<EducationResponse> getById(@PathVariable Long id) {
        EducationResponse response = educationService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{cvId}/educations")
    public ResponseEntity<List<EducationResponse>> getByCvId(@PathVariable Long cvId) {

        List<EducationResponse> response = educationService.getByCvId(cvId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/educations/{id}")
    public ResponseEntity<EducationResponse> update(@PathVariable Long id, @Valid @RequestBody UpdateEducationRequest request) {
        EducationResponse response = educationService.update(id, request);
        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/educations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        educationService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
