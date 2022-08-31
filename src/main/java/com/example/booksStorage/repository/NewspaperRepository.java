package com.example.booksStorage.repository;

import com.example.booksStorage.domain.Newspaper;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewspaperRepository extends CrudRepository<Newspaper, Long> {
}