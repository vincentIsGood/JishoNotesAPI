package com.vincentcodes.jishoapi.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException() {
    }

    public UserNotFoundException(String username) {
        super(username);
    }
}
