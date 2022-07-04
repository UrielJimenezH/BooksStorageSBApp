package com.example.booksStorage.validations;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NonBlankStringValidator extends BaseValidator {
    public NonBlankStringValidator(Validator next) {
        super(next);
    }
    @Override
    public Object validate(Object data) {
        if (data instanceof String && ((String) data).isBlank())
            throw new IllegalArgumentException("Blank string");

        return super.validate(data);
    }
}
