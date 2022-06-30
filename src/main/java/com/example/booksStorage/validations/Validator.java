package com.example.booksStorage.validations;

public interface Validator<T> {
    void setNext(Validator<T> validator);
    T validate(T data);
}
