package com.fdmgroup.api.exception;

public class InvalidCredentialsException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String errorMessage;

	public InvalidCredentialsException() {
		super();
	}

	public InvalidCredentialsException(String message) {
		super(message);
		this.errorMessage = message;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
