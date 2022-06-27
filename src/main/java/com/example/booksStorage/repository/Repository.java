package com.example.booksStorage.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<K, E> {
    Optional<E> get(Comparable<K> key);

    List<E> getAll();

    void save(Comparable<K> key, E element);
    void update(Comparable<K> key, E element);
    boolean delete(Comparable<K> key);
}
