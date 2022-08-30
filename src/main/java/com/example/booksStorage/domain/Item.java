package com.example.booksStorage.domain;

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
    private Long holderId = null;

    public Item(
            String summary,
            int numberOfPages,
            LocalDate releaseDate
    ) {
        this.summary = Validations.validate(summary);
        this.numberOfPages = Validations.validate(numberOfPages);
        this.releaseDate = Validations.validate(releaseDate);

        this.registrationDate = LocalDate.now();
    }
}
