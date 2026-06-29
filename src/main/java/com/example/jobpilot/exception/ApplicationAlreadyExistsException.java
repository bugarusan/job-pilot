package com.example.jobpilot.exception;

public class ApplicationAlreadyExistsException extends RuntimeException {

    public ApplicationAlreadyExistsException(Long userId, Long jobId) {
        super("Application already exists for user " + userId + " and job " + jobId);
    }

}
