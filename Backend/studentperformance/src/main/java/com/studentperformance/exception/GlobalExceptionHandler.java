package com.studentperformance.exception;

import jakarta.persistence.PersistenceException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFound(
            ResourceNotFoundException ex, 
            HttpServletRequest req) {
        logger.warn("Resource not found: {}", ex.getMessage());
        ApiError apiError = new ApiError(
            HttpStatus.NOT_FOUND.value(), 
            "Not Found", 
            ex.getMessage(), 
            req.getRequestURI()
        );
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationErrors(
            MethodArgumentNotValidException ex, 
            HttpServletRequest req) {
        ApiError apiError = new ApiError(
            HttpStatus.BAD_REQUEST.value(), 
            "Validation Error", 
            "Input validation failed", 
            req.getRequestURI()
        );
        
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            apiError.addDetail(error.getField() + ": " + error.getDefaultMessage());
        }
        
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex, 
            HttpServletRequest req) {
        String message = String.format("Invalid value '%s' for parameter '%s'", 
            ex.getValue(), ex.getName());
        ApiError apiError = new ApiError(
            HttpStatus.BAD_REQUEST.value(), 
            "Type Mismatch", 
            message, 
            req.getRequestURI()
        );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({DataAccessException.class, PersistenceException.class})
    public ResponseEntity<ApiError> handleDatabaseErrors(
            Exception ex, 
            HttpServletRequest req) {
        logger.error("Database error occurred", ex);
        ApiError apiError = new ApiError(
            HttpStatus.INTERNAL_SERVER_ERROR.value(), 
            "Database Error", 
            "An error occurred while accessing the database", 
            req.getRequestURI()
        );
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MLServiceException.class)
    public ResponseEntity<ApiError> handleMLServiceError(
            MLServiceException ex, 
            HttpServletRequest req) {
        logger.error("ML Service error", ex);
        ApiError apiError = new ApiError(
            HttpStatus.BAD_GATEWAY.value(), 
            "ML Service Error", 
            ex.getMessage(), 
            req.getRequestURI()
        );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthenticationError(
            AuthenticationException ex, 
            HttpServletRequest req) {
        logger.warn("Authentication failed: {}", ex.getMessage());
        ApiError apiError = new ApiError(
            HttpStatus.UNAUTHORIZED.value(), 
            "Unauthorized", 
            "Authentication failed", 
            req.getRequestURI()
        );
        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDenied(
            AccessDeniedException ex, 
            HttpServletRequest req) {
        logger.warn("Access denied: {}", ex.getMessage());
        ApiError apiError = new ApiError(
            HttpStatus.FORBIDDEN.value(), 
            "Forbidden", 
            "You don't have permission to access this resource", 
            req.getRequestURI()
        );
        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgument(
            IllegalArgumentException ex, 
            HttpServletRequest req) {
        ApiError apiError = new ApiError(
            HttpStatus.BAD_REQUEST.value(), 
            "Invalid Argument", 
            ex.getMessage(), 
            req.getRequestURI()
        );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericError(
            Exception ex, 
            HttpServletRequest req) {
        logger.error("Unhandled exception occurred", ex);
        ApiError apiError = new ApiError(
            HttpStatus.INTERNAL_SERVER_ERROR.value(), 
            "Internal Server Error", 
            "An unexpected error occurred", 
            req.getRequestURI()
        );
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}