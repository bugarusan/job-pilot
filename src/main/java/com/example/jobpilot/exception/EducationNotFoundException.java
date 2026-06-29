package com.example.jobpilot.exception;

public class EducationNotFoundException extends RuntimeException {
    public EducationNotFoundException(Long id) {
        super("Education not found with id: " + id);
    }
}
