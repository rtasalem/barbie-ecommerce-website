package com.fdmgroup.api.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fdmgroup.api.exception.ItemNameExistsException;
import com.fdmgroup.api.exception.ResourceNotFoundException;

@RestControllerAdvice
public class ItemControllerAdvice {

	private final static Logger log = LoggerFactory.getLogger(ItemControllerAdvice.class);

	@ExceptionHandler(value = ResourceNotFoundException.class)
	public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
		log.info("Entering handleResourceNotFoundException");
		log.error("ResourceNotFoundException occurred: {}", ex.getMessage());
		log.info("Exiting handleResourceNotFoundException");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

	@ExceptionHandler(value = ItemNameExistsException.class)
	public ResponseEntity<String> handleItemNameExistsException(ItemNameExistsException ex) {
		log.info("Entering handleItemNameExistsException");
		log.error("ItemNameExistsException occurred: {}", ex.getMessage());
		log.info("Exiting handleItemNameExistsException");
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
	}

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		log.info("Entering handleMethodArgumentNotValidException");
		List<ObjectError> errors = ex.getAllErrors();
		StringBuilder sb = new StringBuilder();
		errors.forEach(error -> sb.append(error.getDefaultMessage() + ", "));
		log.error("An error occurred while handling MethodArgumentNotValidException", ex);
		log.info("Exiting handleMethodArgumentNotValidException");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(sb.toString());
	}

}
