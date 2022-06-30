package com.example.booksStorage.validations;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class MinimumNumberOfPagesValidator extends BaseValidator<Integer> {
    private int minNumberOfPages = 10;

    public MinimumNumberOfPagesValidator(int minNumberOfPages, Validator<Integer> next) {
        super(next);
        this.minNumberOfPages = minNumberOfPages;
    }
    @Override
    public Integer validate(Integer data) {
        if (data < minNumberOfPages)
            throw new IllegalArgumentException("Invalid number of pages");
        else
            return super.validate(data);
    }
}
