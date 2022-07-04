package com.example.booksStorage.validations;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class MinimumNumberOfPagesValidator extends BaseValidator {
    private int minNumberOfPages = 10;

    public MinimumNumberOfPagesValidator(int minNumberOfPages, Validator next) {
        super(next);
        this.minNumberOfPages = minNumberOfPages;
    }
    @Override
    public Object validate(Object data) {
        if (data instanceof Integer && (Integer) data < minNumberOfPages)
            throw new IllegalArgumentException("Invalid number of pages");

        return super.validate(data);
    }
}
