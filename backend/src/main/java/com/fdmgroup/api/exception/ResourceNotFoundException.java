package com.fdmgroup.api.exception;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -7025887842249512069L;

	public ResourceNotFoundException(String message) {
		super(message);
	}

}
