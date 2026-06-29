package com.example.jobpilot.service;

import com.example.jobpilot.entity.Job;

import java.util.List;

public interface JobService {

    Job getById(Long id);

    List<Job> getAll();

}
