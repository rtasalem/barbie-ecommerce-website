package com.fdmgroup.api.exception;

public class EmailAlreadyUsedException extends RuntimeException {

    private static final long serialVersionUID = -3468822329048865426L;

    public EmailAlreadyUsedException(String message) {
        super(message);
    }
}
