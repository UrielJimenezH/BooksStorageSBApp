package com.example.booksStorage.validations;

public interface Validator {
    void setNext(Validator validator);
    Object validate(Object data);
}
