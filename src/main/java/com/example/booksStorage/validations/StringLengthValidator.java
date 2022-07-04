package com.example.booksStorage.validations;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StringLengthValidator extends BaseValidator {
    private int minLength;
    private int maxLength;

    public StringLengthValidator(
            int minLength,
            int maxLength,
            Validator next
    ) {
        super(next);
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    @Override
    public Object validate(Object data) {
        if (!(data instanceof String))
            return super.validate(data);

        String str = (String) data;

        if (str.length() < minLength || str.length() > maxLength)
            throw new IllegalArgumentException("String length outside bounds");

        return super.validate(data);
    }
}
