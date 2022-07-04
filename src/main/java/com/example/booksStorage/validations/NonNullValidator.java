package com.example.booksStorage.validations;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NonNullValidator<T> extends BaseValidator<T> {
    public NonNullValidator(Validator<T> next) {
        super(next);
    }

    @Override
    public T validate(T data) {
        if (data == null)
            throw new IllegalArgumentException("Null value passed");
        else
            return super.validate(data);
    }
}
