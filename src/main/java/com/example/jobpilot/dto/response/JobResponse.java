package com.example.jobpilot.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobResponse {

    private Long id;

    private String title;

    private String company;

    private String description;

    private String location;

    private String salary;

    private List<String> requiredSkills;

    private String url;

    private LocalDateTime createdAt;
}
