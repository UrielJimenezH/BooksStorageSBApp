package com.example.booksStorage.validations;

import java.time.LocalDate;

public class PreviousToCurrentDateValidator extends BaseValidator<LocalDate> {
    @Override
    public LocalDate validate(LocalDate date) {
        if (date.isAfter(LocalDate.now()))
            throw new IllegalArgumentException();

        return super.validate(date);
    }
}
