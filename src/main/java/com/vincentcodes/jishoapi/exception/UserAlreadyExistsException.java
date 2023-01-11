package com.vincentcodes.jishoapi.exception;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException() {
    }

    public UserAlreadyExistsException(String username) {
        super(username);
    }
}
