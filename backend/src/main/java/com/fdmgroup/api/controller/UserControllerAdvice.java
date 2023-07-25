package com.fdmgroup.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.fdmgroup.api.exception.*;
import com.fdmgroup.api.exception.EmailAlreadyUsedException;

@RestControllerAdvice
public class UserControllerAdvice {

    private final static Logger log = LoggerFactory.getLogger(UserControllerAdvice.class);

    @ExceptionHandler(value = InvalidCredentialsException.class)
    public ResponseEntity<String> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        log.info("Entering handleInvalidCredentialsException");
        log.error("InvalidCredentialsException occurred: {}", ex.getMessage());
        log.info("Exiting handleInvalidCredentialsException");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(value = EmailAlreadyUsedException.class)
    public ResponseEntity<String> handleEmailAlreadyUsedException(EmailAlreadyUsedException ex) {
        log.info("Entering handleEmailAlreadyUsedException");
        log.error("EmailAlreadyUsedException occurred: {}", ex.getMessage());
        log.info("Exiting handleEmailAlreadyUsedException");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(value = MissingRequiredFieldException.class)
    public ResponseEntity<String> handleMissingRequiredFieldException(MissingRequiredFieldException ex) {
        log.info("Entering handleMissingRequiredFieldException");
        log.error("MissingRequiredFieldException occurred: {}", ex.getMessage());
        log.info("Exiting handleMissingRequiredFieldException");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
