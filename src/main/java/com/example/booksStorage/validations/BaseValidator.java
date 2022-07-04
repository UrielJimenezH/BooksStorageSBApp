package com.example.booksStorage.validations;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseValidator implements Validator {
    private Validator next;

    @Override
    public void setNext(Validator validator) {
        this.next = validator;
    }

    @Override
    public Object validate(Object data) {
        if (next != null)
            return next.validate(data);
        else
            return data;
    }
}
