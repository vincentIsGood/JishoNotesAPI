package com.vincentcodes.jishoapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "invalid operation")
public class InvalidOperation extends RuntimeException{
    public InvalidOperation() {
    }

    public InvalidOperation(String message) {
        super(message);
    }
}
