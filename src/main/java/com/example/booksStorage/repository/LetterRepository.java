package com.example.booksStorage.repository;

import com.example.booksStorage.domain.Letter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LetterRepository extends CrudRepository<Letter, Long> {
}
