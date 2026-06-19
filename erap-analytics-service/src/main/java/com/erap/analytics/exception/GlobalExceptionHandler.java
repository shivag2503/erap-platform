package com.erap.analytics.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @Value("${spring.application.name}")
    private String serviceName;

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDataNotFound(
            DataNotFoundException ex, HttpServletRequest request) {
        return ResponseEntity.status(404).body(
                ErrorResponse.builder()
                        .timestamp(LocalDateTime.now()).status(404)
                        .error("Data Not Found").message("Data is not available")
                        .path(request.getRequestURI()).service(serviceName)
                        .build()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex,
                                                          HttpServletRequest request) {
        var fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> new ErrorResponse.FieldError(e.getField(), e.getDefaultMessage()))
                .toList();

        return ResponseEntity.status(400).body(
                ErrorResponse.builder().timestamp(LocalDateTime.now()).status(400)
                        .error("Bad Request")
                        .message(ex.getMessage())
                        .path(request.getRequestURI()).service(serviceName)
                        .errors(fieldErrors).build()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(
            Exception ex, HttpServletRequest request) {
        log.error("Unexpected error at {}: {}", request.getRequestURI(), ex.getMessage(), ex);
        return ResponseEntity.status(500).body(
                ErrorResponse.builder()
                        .timestamp(LocalDateTime.now()).status(500)
                        .error("Internal Server Error")
                        .message("An unexpected error occurred")
                        .path(request.getRequestURI())
                        .service(serviceName)
                        .build()
        );
    }

}