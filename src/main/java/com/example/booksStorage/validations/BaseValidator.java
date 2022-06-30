package com.example.booksStorage.validations;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseValidator<T> implements Validator<T> {
    private Validator<T> next;

    @Override
    public void setNext(Validator<T> validator) {
        this.next = validator;
    }

    @Override
    public T validate(T data) {
        if (next != null)
            return next.validate(data);
        else
            return data;
    }
}
