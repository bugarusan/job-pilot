package com.example.jobpilot.controller;

import com.example.jobpilot.dto.request.CreateSkillRequest;
import com.example.jobpilot.dto.request.UpdateSkillRequest;
import com.example.jobpilot.dto.response.SkillResponse;
import com.example.jobpilot.service.SkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cvs")
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;

    @PostMapping("/{cvId}/skills")
    public ResponseEntity<SkillResponse> create(@PathVariable Long cvId, @Valid @RequestBody CreateSkillRequest request) {
        SkillResponse response = skillService.create(cvId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/skills/{id}")
    public ResponseEntity<SkillResponse> getById(@PathVariable Long id) {
        SkillResponse response = skillService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{cvId}/skills")
    public ResponseEntity<List<SkillResponse>> getByCvId(@PathVariable Long cvId) {

        List<SkillResponse> response = skillService.getByCvId(cvId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/skills/{id}")
    public ResponseEntity<SkillResponse> update(@PathVariable Long id, @Valid @RequestBody UpdateSkillRequest request) {
        SkillResponse response = skillService.update(id, request);
        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/skills/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        skillService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
