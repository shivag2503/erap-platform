package com.erap.analytics.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ErrorResponse {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private String service;
    private List<FieldError> errors;

    @Data
    @AllArgsConstructor
    public static class FieldError {

        private String field;
        private String message;
    }

}