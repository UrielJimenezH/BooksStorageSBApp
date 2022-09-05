package com.example.booksStorage.dto;

import com.example.booksStorage.validations.Validations;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public final class LetterDto extends ItemDto {
    private String author;

    public LetterDto(
            String summary,
            Integer numberOfPages,
            LocalDate releaseDate,
            String author
    ) {
        super(summary, numberOfPages, releaseDate);

        this.author = Validations.validate(author);
    }
}
