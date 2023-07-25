package com.fdmgroup.api.controller.test;

import com.fdmgroup.api.controller.UserControllerAdvice;
import com.fdmgroup.api.exception.EmailAlreadyUsedException;
import com.fdmgroup.api.exception.InvalidCredentialsException;
import com.fdmgroup.api.exception.MissingRequiredFieldException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserControllerAdviceTest {

    @InjectMocks
    private UserControllerAdvice userControllerAdvice;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testHandleInvalidCredentialsException() {
        InvalidCredentialsException ex = new InvalidCredentialsException("Invalid credentials");
        ResponseEntity<String> response = userControllerAdvice.handleInvalidCredentialsException(ex);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid credentials", response.getBody());
    }

    @Test
    public void testHandleEmailAlreadyUsedException() {
        EmailAlreadyUsedException ex = new EmailAlreadyUsedException("Email is already used");
        ResponseEntity<String> response = userControllerAdvice.handleEmailAlreadyUsedException(ex);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Email is already used", response.getBody());
    }

    @Test
    public void testHandleMissingRequiredFieldException() {
        MissingRequiredFieldException ex = new MissingRequiredFieldException("Required fields are missing");
        ResponseEntity<String> response = userControllerAdvice.handleMissingRequiredFieldException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Required fields are missing", response.getBody());
    }
}
