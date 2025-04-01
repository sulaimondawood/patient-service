package com.dawood.patient_service.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handlePatientNotFound(MethodArgumentNotValidException e){
        Map<String, String> response = new HashMap<>();

        e.getBindingResult().getFieldErrors()
                .forEach((err)->response.put(err.getField(), err.getDefaultMessage()));

        return ResponseEntity.badRequest().body(response);
    }
}
