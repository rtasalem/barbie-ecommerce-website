package com.fdmgroup.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fdmgroup.api.exception.ResourceNotFoundException;

@RestControllerAdvice
public class BasketControllerAdvice {

	private final static Logger log = LoggerFactory.getLogger(BasketControllerAdvice.class);
	
	@ExceptionHandler(value = ResourceNotFoundException.class)
	public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
		log.info("Entering handleResourceNotFoundException");
		log.error("ResourceNotFoundException occurred: {}", ex.getMessage());
		log.info("Exiting handleResourceNotFoundException");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}
	
}
