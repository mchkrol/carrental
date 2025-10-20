package com.michalkrol.carrental.validation;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RentalValidationExceptionHandler {

    @ExceptionHandler(RentalValidationException.class)
    public ResponseEntity<String> handleValidationException(RentalValidationException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }
}
