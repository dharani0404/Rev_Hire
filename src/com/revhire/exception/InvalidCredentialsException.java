package com.revhire.exception;

public class InvalidCredentialsException extends BusinessException {

    private static final long serialVersionUID = 1L;

    public InvalidCredentialsException(String message) {
        super(message);
    }
}
