package com.example.booksStorage;

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

    public Item(
            String summary,
            int numberOfPages,
            LocalDate releaseDate
    ) {
        validate(summary, numberOfPages, releaseDate);

        this.id = nextId++;
        this.summary = summary;
        this.numberOfPages = numberOfPages;
        this.releaseDate = releaseDate;

        this.registrationDate = LocalDate.now();
    }

    private void validate(String summary, int numberOfPages, LocalDate releaseDate) {
        requireNonNullParams(summary, numberOfPages, releaseDate);

        if (summary.isBlank())
            throw new IllegalArgumentException();

        if (numberOfPages < 1)
            throw new IllegalArgumentException("Invalid number of pages: " + numberOfPages);

        if (releaseDate.isAfter(LocalDate.now()))
            throw new IllegalArgumentException();
    }

    protected void requireNonNullParams(Object... arrayOfObj) {
        for (Object obj: arrayOfObj) {
            if (obj == null)
                throw new IllegalArgumentException();
        }
    }
}
