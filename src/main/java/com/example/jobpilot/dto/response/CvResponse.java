package com.example.jobpilot.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CvResponse {

    private Long id;

    private String fullName;

    private String email;

    private String phone;

    private String summary;

    private String title;

    private boolean isDefault;

    private List<ExperienceResponse> experiences;

    private List<EducationResponse> educations;

    private List<SkillResponse> skills;


}
