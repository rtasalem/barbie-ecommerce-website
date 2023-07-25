package com.fdmgroup.api.exception;

public class MissingRequiredFieldException extends RuntimeException {

    private static final long serialVersionUID = -7025887842249512069L;

    public MissingRequiredFieldException(String message) {
        super(message);
    }
}
