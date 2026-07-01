package com.example.jobpilot.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExperienceResponse {

    private Long id;

    private String company;

    private String position;

    private LocalDate startDate;

    private LocalDate endDate;

    private String description;
}
