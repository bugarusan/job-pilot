package com.example.jobpilot.dto.response;

import com.example.jobpilot.enums.SkillLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SkillResponse {

    private Long id;

    private String name;

    private SkillLevel level;
}
