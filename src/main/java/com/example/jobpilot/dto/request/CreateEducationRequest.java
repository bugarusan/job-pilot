package com.example.jobpilot.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateEducationRequest {

    private String school;

    private String degree;

    private String field;

    private LocalDate startDate;

    private LocalDate endDate;
}