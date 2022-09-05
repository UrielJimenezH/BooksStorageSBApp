package com.example.booksStorage.dto;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@ToString
@EqualsAndHashCode
public final class UserDto {
    private Long id;
    private String name;
    private String lastname;
    private String address;
    private LocalDate dateOfBirth;
    private String username;
    private String password;
    private LocalDate registrationDate;

    public UserDto() {
        this.registrationDate = LocalDate.now();
    }

    public UserDto(
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
