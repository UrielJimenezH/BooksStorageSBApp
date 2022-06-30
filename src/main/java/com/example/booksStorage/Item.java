package com.example.booksStorage;

import com.example.booksStorage.validations.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public abstract class Item {
    private Long id;
    private String summary;
    private Integer numberOfPages;
    private LocalDate releaseDate;
    private LocalDate registrationDate;

    private static long nextId = 1;

    /**Validators using Chain of Responsibility*/
    private static Validator<String> stringValidator = new NonNullValidator<>(
            new NonBlankStringValidator(
                    new StringLengthValidator(5, 30)
            )
    );

    private static Validator<Integer> minimumNumberOfPagesValidator = new NonNullValidator<>(
            new MinimumNumberOfPagesValidator(3)
    );
    
    private static Validator<LocalDate> previousToCurrentDateValidator = new NonNullValidator<>(
            new PreviousToCurrentDateValidator()
    );

    public Item(
            String summary,
            int numberOfPages,
            LocalDate releaseDate
    ) {
        this.id = nextId++;
        this.summary = validate(summary);
        this.numberOfPages = validate(numberOfPages);
        this.releaseDate = validate(releaseDate);

        this.registrationDate = LocalDate.now();
    }

    protected String validate(String string) {
        return stringValidator.validate(string);
    }

    protected Integer validate(Integer num) {
        return minimumNumberOfPagesValidator.validate(num);
    }

    protected LocalDate validate(LocalDate date) {
        return previousToCurrentDateValidator.validate(date);
    }
}
