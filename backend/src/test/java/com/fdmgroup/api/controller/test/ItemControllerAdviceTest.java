package com.fdmgroup.api.controller.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.fdmgroup.api.controller.ItemController;
import com.fdmgroup.api.controller.ItemControllerAdvice;
import com.fdmgroup.api.exception.ItemNameExistsException;
import com.fdmgroup.api.exception.ResourceNotFoundException;
import com.fdmgroup.api.service.ItemService;

@ExtendWith(MockitoExtension.class)
public class ItemControllerAdviceTest {

	@Mock
	private ItemService itemService;

	@InjectMocks
	private ItemController itemController;

	@InjectMocks
	private ItemControllerAdvice itemControllerAdvice;

	private MockMvc mockMvc;

	@Test
	void handleResourceNotFoundException() throws Exception {
		int nonExistingItemId = 1000;
		when(itemService.findItemById(nonExistingItemId)).thenThrow(new ResourceNotFoundException("Item not found"));

		mockMvc = MockMvcBuilders.standaloneSetup(itemController).setControllerAdvice(new ItemControllerAdvice())
				.build();

		mockMvc.perform(get("/api/v1/items/" + nonExistingItemId)).andExpect(status().isNotFound())
				.andExpect(jsonPath("$").value("Item not found"));
	}

	@Test
	public void testHandleMethodArgumentNotValidException() {
		MethodArgumentNotValidException exceptionMock = mock(MethodArgumentNotValidException.class);
		List<ObjectError> errors = new ArrayList<>();

		FieldError fieldError1 = new FieldError("item", "name", "Item name must not be left blank.");
		FieldError fieldError2 = new FieldError("item", "description", "Item description must not be left blank.");

		errors.add(fieldError1);
		errors.add(fieldError2);

		when(exceptionMock.getAllErrors()).thenReturn(errors);

		ResponseEntity<String> expectedResponse = ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body("Item name must not be left blank., Item description must not be left blank., ");

		ResponseEntity<String> actualResponse = itemControllerAdvice
				.handleMethodArgumentNotValidException(exceptionMock);
		assertEquals(expectedResponse, actualResponse);
	}

	@Test
	public void testHandleItemNameExistsException() {
		ItemNameExistsException exceptionMock = new ItemNameExistsException("Item name already exists");

		ResponseEntity<String> expectedResponse = ResponseEntity.status(HttpStatus.CONFLICT)
				.body("Item name already exists");

		ResponseEntity<String> actualResponse = itemControllerAdvice.handleItemNameExistsException(exceptionMock);
		assertEquals(expectedResponse, actualResponse);
	}

}
