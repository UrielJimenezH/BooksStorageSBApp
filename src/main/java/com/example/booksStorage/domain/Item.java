package com.example.booksStorage.domain;

import com.example.booksStorage.validations.*;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;

@MappedSuperclass
@Getter
@Setter
@ToString
@EqualsAndHashCode
public abstract class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "summary")
    private String summary;
    @Column(name = "number_of_pages")
    private Integer numberOfPages;
    @Column(name = "release_date")
    private LocalDate releaseDate;
    @Column(name = "registration_date")
    private LocalDate registrationDate;
    @Column(name = "holder_id")
    private Long holderId = null;


    public Item() {

    }

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
