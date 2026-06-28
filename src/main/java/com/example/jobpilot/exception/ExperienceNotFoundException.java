package com.example.jobpilot.exception;

public class ExperienceNotFoundException extends RuntimeException {
    public ExperienceNotFoundException(Long id) {
        super("Experience not found with id: " + id);
    }
}
