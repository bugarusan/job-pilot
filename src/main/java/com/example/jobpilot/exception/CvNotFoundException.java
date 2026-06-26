package com.example.jobpilot.exception;

public class CvNotFoundException extends RuntimeException {

    public CvNotFoundException(Long id) {
        super("CV not found with id: " + id);
    }

}