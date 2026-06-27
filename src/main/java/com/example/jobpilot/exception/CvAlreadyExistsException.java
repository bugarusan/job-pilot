package com.example.jobpilot.exception;

public class CvAlreadyExistsException extends RuntimeException {

    public CvAlreadyExistsException(Long userId) {
        super("User already has a CV. User ID: " + userId);
    }

}