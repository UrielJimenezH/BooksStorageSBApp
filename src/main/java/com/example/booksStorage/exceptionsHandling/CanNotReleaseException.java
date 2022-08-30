package com.example.booksStorage.exceptionsHandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CanNotReleaseException extends RuntimeException {
    public CanNotReleaseException(Long id) {
        super("Element can not be released by User with id " + id);
    }
}
