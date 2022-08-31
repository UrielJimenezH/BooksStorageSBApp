package com.example.booksStorage.domain;

import com.example.booksStorage.validations.Validations;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "Newspapers")
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NamedQuery(query = "SELECT n FROM Newspaper n", name = "query_find_all_newspapers")
public final class Newspaper extends Item {
    @Column(name = "title")
    private String title;
    @Column(name = "publisher")
    private String publisher;

    public Newspaper() {
        super();
    }

    public Newspaper(
            String summary,
            Integer numberOfPages,
            LocalDate releaseDate,
            String title,
            String publisher
    ) {
        super(summary, numberOfPages, releaseDate);

        this.title = Validations.validate(title);
        this.publisher = Validations.validate(publisher);
    }
}
