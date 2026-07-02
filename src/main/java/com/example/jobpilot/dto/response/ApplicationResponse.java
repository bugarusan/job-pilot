package com.example.jobpilot.dto.response;

import com.example.jobpilot.enums.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationResponse {

    private Long id;

    private JobResponse job;

    private ApplicationStatus status;

    private LocalDateTime appliedAt;
}
