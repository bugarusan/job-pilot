package com.example.jobpilot.dto.request;

import com.example.jobpilot.enums.SkillLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateSkillRequest {

    private String name;

    private SkillLevel level;
}
