DROP DATABASE IF EXISTS BooksStorage;

CREATE DATABASE BooksStorage;

USE BooksStorage;

DROP TABLE IF EXISTS Users;

CREATE TABLE Users(
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    address VARCHAR(255),
    date_of_birth DATE NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    registration_date DATE NOT NULL
);

DROP TABLE IF EXISTS Books;

CREATE TABLE Books(
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    publisher VARCHAR(255) NOT NULL,
    edition VARCHAR(255) NOT NULL,
    summary VARCHAR(255) NOT NULL,
    number_of_pages INT NOT NULL,
    release_date DATE NOT NULL,
    registration_date DATE NOT NULL,
    holder_id INT DEFAULT NULL,
    CONSTRAINT fk_book_holder_id FOREIGN KEY(holder_id)
    REFERENCES Users(id)
);


DROP TABLE IF EXISTS Letters;

CREATE TABLE Letters(
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    author VARCHAR(255) NOT NULL,
    summary VARCHAR(255) NOT NULL,
    number_of_pages INT NOT NULL,
    release_date DATE NOT NULL,
    registration_date DATE NOT NULL,
    holder_id INT DEFAULT NULL,
    CONSTRAINT fk_letter_holder_id FOREIGN KEY(holder_id)
    REFERENCES Users(id)
);


DROP TABLE IF EXISTS Newspapers;

CREATE TABLE Newspapers(
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    publisher VARCHAR(255) NOT NULL,
    summary VARCHAR(255) NOT NULL,
    number_of_pages INT NOT NULL,
    release_date DATE NOT NULL,
    registration_date DATE NOT NULL,
    holder_id INT DEFAULT NULL,
    CONSTRAINT fk_newspaper_holder_id FOREIGN KEY(holder_id)
    REFERENCES Users(id)
);


DROP TABLE IF EXISTS Magazines;

CREATE TABLE Magazines(
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    publisher VARCHAR(255) NOT NULL,
    summary VARCHAR(255) NOT NULL,
    number_of_pages INT NOT NULL,
    release_date DATE NOT NULL,
    registration_date DATE NOT NULL,
    holder_id INT DEFAULT NULL,
    CONSTRAINT fk_magazine_holder_id FOREIGN KEY(holder_id)
    REFERENCES Users(id)
);