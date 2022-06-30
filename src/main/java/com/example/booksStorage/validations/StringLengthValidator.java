package com.example.booksStorage.validations;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StringLengthValidator extends BaseValidator<String> {
    private int minLength;
    private int maxLength;

    public StringLengthValidator(
            int minLength,
            int maxLength,
            Validator<String> next
    ) {
        super(next);
    }

    @Override
    public String validate(String data) {
        if (data.length() < minLength || data.length() > maxLength)
            throw new IllegalArgumentException("String length outside bounds");
        else
            return super.validate(data);
    }
}
