package com.fdmgroup.api.controller.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fdmgroup.api.controller.BasketControllerAdvice;
import com.fdmgroup.api.exception.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
public class BasketControllerAdviceTest {
	

    @InjectMocks
    private BasketControllerAdvice advice;

    @Test
    public void testHandleResourceNotFoundException() {
        String errorMessage = "Resource not found";
        ResourceNotFoundException exception = new ResourceNotFoundException(errorMessage);

        ResponseEntity<String> responseEntity = advice.handleResourceNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(errorMessage, responseEntity.getBody());
    }
}
