package com.fdmgroup.api.exception;

public class FavouriteAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = -2791904211795544170L;

	public FavouriteAlreadyExistsException(String message) {
		super(message);
	}

}
