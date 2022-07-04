package com.example.booksStorage.validations;

import lombok.NoArgsConstructor;
import java.time.LocalDate;

@NoArgsConstructor
public class PreviousToCurrentDateValidator extends BaseValidator<LocalDate> {
    @Override
    public LocalDate validate(LocalDate date) {
        if (date.isAfter(LocalDate.now()))
            throw new IllegalArgumentException();

        return super.validate(date);
    }
}
