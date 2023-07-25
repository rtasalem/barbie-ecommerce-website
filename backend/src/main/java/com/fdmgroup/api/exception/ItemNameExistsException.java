package com.fdmgroup.api.exception;

public class ItemNameExistsException extends RuntimeException {

	private static final long serialVersionUID = 3466822329048865426L;

	public ItemNameExistsException(String message) {
		super(message);
	}

}
