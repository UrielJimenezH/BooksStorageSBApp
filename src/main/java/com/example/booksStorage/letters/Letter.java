package com.example.booksStorage.letters;

import com.example.booksStorage.Item;
import com.example.booksStorage.validations.Validations;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDate;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class Letter extends Item {
    private String author;

    public Letter(
            String summary,
            Integer numberOfPages,
            LocalDate releaseDate,
            String author
    ) {
        super(summary, numberOfPages, releaseDate);

        this.author = Validations.validate(author);
    }
}
