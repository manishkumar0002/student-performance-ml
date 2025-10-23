package com.studentperformance.exception;

/**
 * Custom exception used when a resource (like username or email)
 * already exists in the database.
 */
public class ResourceAlreadyExistsException extends RuntimeException {

    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}
