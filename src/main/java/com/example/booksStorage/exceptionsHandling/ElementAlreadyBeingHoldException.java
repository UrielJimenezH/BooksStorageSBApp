package com.example.booksStorage.exceptionsHandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ElementAlreadyBeingHoldException extends RuntimeException {
    public ElementAlreadyBeingHoldException() {
        super("Element is already being hold by another user");
    }
}
