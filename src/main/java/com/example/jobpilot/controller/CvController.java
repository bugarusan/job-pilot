package com.example.jobpilot.controller;

import com.example.jobpilot.dto.request.CreateCvRequest;
import com.example.jobpilot.dto.request.UpdateCvRequest;
import com.example.jobpilot.dto.response.CvResponse;
import com.example.jobpilot.service.CvService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class CvController {

    private final CvService cvService;

    @PostMapping("/{userId}/cvs")
    public ResponseEntity<CvResponse> create(
            @PathVariable Long userId,
            @Valid @RequestBody CreateCvRequest request) {

        CvResponse response = cvService.create(userId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/cvs/{id}")
    public ResponseEntity<CvResponse> getById(@PathVariable Long id) {

        CvResponse response = cvService.getById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}/cvs")
    public ResponseEntity<List<CvResponse>> getByUserId(@PathVariable Long userId) {

        List<CvResponse> response = cvService.getByUserId(userId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/cvs/{id}")
    public ResponseEntity<CvResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCvRequest request) {

        CvResponse response = cvService.update(id, request);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/cvs/{id}/default")
    public ResponseEntity<CvResponse> setDefault(@PathVariable Long id) {

        CvResponse response = cvService.setDefault(id);

        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/cvs/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        cvService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
