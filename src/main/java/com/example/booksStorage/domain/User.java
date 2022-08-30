package com.example.booksStorage.domain;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public final class User {
    private Long id;
    private String name;
    private String lastname;
    private String address;
    private LocalDate dateOfBirth;
    private String username;
    private String password;
    private LocalDate registrationDate;

    public User(
            String name,
            String lastname,
            String address,
            LocalDate dateOfBirth,
            String username,
            String password
    ) {
        this.name = name;
        this.lastname = lastname;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.username = username;
        this.password = password;

        this.registrationDate = LocalDate.now();
    }
}
