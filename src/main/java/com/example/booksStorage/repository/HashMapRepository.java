package com.example.booksStorage.repository;

import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class HashMapRepository<K, E> implements Repository<K, E> {
    private final Map<Comparable<K>, E> map = new HashMap<>();
    @Override
    public Optional<E> get(Comparable<K> key) {
        return Optional.ofNullable(map.get(key));
    }

    @Override
    public List<E> getAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public void save(Comparable<K> key, E element) {
        map.put(key, element);
    }

    @Override
    public void update(Comparable<K> key, E element) {
        map.putIfAbsent(key, element);
    }

    @Override
    public boolean delete(Comparable<K> key) {
        return map.remove(key) != null;
    }
}
