package com.fdmgroup.api.controller.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fdmgroup.api.controller.FavouritesControllerAdvice;
import com.fdmgroup.api.exception.FavouriteAlreadyExistsException;
import com.fdmgroup.api.exception.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
public class FavouritesControllerAdviceTest {

	@InjectMocks
	private FavouritesControllerAdvice advice;

	@Test
	public void testHandleResourceNotFoundException() {
		// Given
		String errorMessage = "Resource not found";
		ResourceNotFoundException exception = new ResourceNotFoundException(errorMessage);

		// When
		ResponseEntity<String> responseEntity = advice.handleResourceNotFoundException(exception);

		// Then
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals(errorMessage, responseEntity.getBody());
	}

	@Test
	public void testHandleFavouriteAlreadyExistsException() {
		// Given
		String errorMessage = "Favourite already exists";
		FavouriteAlreadyExistsException exception = new FavouriteAlreadyExistsException(errorMessage);

		// When
		ResponseEntity<String> responseEntity = advice.handleFavouriteAlreadyExistsException(exception);

		// Then
		assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
		assertEquals(errorMessage, responseEntity.getBody());
	}

}
