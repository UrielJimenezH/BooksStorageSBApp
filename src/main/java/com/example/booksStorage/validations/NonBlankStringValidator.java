package com.example.booksStorage.validations;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NonBlankStringValidator extends BaseValidator<String> {
    public NonBlankStringValidator(Validator<String> next) {
        super(next);
    }
    @Override
    public String validate(String data) {
        if (data.isBlank())
            throw new IllegalArgumentException("Blank string");
        else
            return super.validate(data);
    }
}
