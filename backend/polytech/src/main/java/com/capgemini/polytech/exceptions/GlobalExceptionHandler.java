package com.capgemini.polytech.exceptions;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> handleResponseStatusException(ResponseStatusException ex) {
        // Create a custom response body
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("status", ex.getStatusCode().toString());
        responseBody.put("message", ex.getReason()); // Include the reason message

        return new ResponseEntity<>(responseBody, ex.getStatusCode());
    }
}

