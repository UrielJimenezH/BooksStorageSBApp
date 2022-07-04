package com.example.booksStorage.validations;

public class Validations {
    private final static Validator stringValidator = new NonNullValidator(
            new MinimumNumberOfPagesValidator(3,
                new PreviousToCurrentDateValidator(
                    new NonBlankStringValidator(
                        new StringLengthValidator(5, 30)
                    )
                )
            )
    );

    @SuppressWarnings("unchecked")
    public static <T> T validate(T data) {
        return (T) stringValidator.validate(data);
    }
}
