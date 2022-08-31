package com.example.booksStorage.repository;

import com.example.booksStorage.domain.Magazine;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MagazineRepository extends CrudRepository<Magazine, Long> {
}
