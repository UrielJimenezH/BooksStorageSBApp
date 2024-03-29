package com.example.booksStorage.domain;

import com.example.booksStorage.validations.Validations;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "Letters")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@NamedQuery(query = "SELECT l FROM Letter l", name = "query_find_all_letters")
public final class Letter extends Item {
    @Column(name = "author")
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
