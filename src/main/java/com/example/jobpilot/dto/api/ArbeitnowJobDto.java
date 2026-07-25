package com.example.jobpilot.dto.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArbeitnowJobDto {

    private String slug;

    private String company_name;

    private String title;

    private String description;

    private boolean remote;

    private String url;

    private List<String> tags;

    private List<String> job_types;

    private String location;

    private Long created_at;

}