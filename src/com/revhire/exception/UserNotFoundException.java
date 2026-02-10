package com.revhire.exception;

public class UserNotFoundException extends BusinessException {

    private static final long serialVersionUID = 1L;

    public UserNotFoundException(String message) {
        super(message);
    }
}
