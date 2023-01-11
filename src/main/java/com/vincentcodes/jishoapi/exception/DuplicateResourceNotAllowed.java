package com.vincentcodes.jishoapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Duplicate resource not allowed. Replay request if needed.")
public class DuplicateResourceNotAllowed extends RuntimeException{
    public DuplicateResourceNotAllowed() {
        super();
    }

    public DuplicateResourceNotAllowed(String message) {
        super(message);
    }
}
