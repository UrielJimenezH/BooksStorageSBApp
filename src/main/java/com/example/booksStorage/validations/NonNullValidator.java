package com.example.booksStorage.validations;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NonNullValidator extends BaseValidator {
    public NonNullValidator(Validator next) {
        super(next);
    }

    @Override
    public Object validate(Object data) {
        if (data == null)
            throw new IllegalArgumentException("Null value passed");
        else
            return super.validate(data);
    }
}
