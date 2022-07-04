package com.example.booksStorage.validations;

import lombok.NoArgsConstructor;
import java.time.LocalDate;

@NoArgsConstructor
public class PreviousToCurrentDateValidator extends BaseValidator {
    public PreviousToCurrentDateValidator(Validator next) {
        super(next);
    }

    @Override
    public Object validate(Object date) {
        if (date instanceof LocalDate && ((LocalDate) date).isAfter(LocalDate.now()))
            throw new IllegalArgumentException();

        return super.validate(date);
    }
}
