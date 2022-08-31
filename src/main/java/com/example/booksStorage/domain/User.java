package com.example.booksStorage.domain;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Users")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NamedQuery(query = "SELECT u FROM User u", name = "query_find_all_users")
public final class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "address")
    private String address;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "registration_date")
    private LocalDate registrationDate;

    public User() {
        this.registrationDate = LocalDate.now();
    }

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
