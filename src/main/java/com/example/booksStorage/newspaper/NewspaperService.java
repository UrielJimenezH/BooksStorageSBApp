package com.example.booksStorage.newspaper;

import com.example.booksStorage.Item;
import com.example.booksStorage.exceptions.NoSuchElementFoundException;
import com.example.booksStorage.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewspaperService {
    private final Repository<Long, Item> repository;

    @Autowired
    public NewspaperService(Repository<Long, Item> repository) {
        this.repository = repository;
    }

    public List<Newspaper> getAll() {
        return repository.getAll()
                .stream()
                .filter(item -> item instanceof Newspaper)
                .map(item -> (Newspaper) item)
                .collect(Collectors.toList());
    }

    public Newspaper get(Long newspaperId) {
        return repository.get(newspaperId)
                .filter(item -> item instanceof Newspaper)
                .map(item -> (Newspaper) item)
                .orElseThrow(() -> new NoSuchElementFoundException("Newspaper with id " + newspaperId + " does not exist"));
    }

    public Newspaper add(Newspaper newspaper) {
        repository.save(newspaper.getId(), newspaper);
        return newspaper;
    }

    public Newspaper update(Long newspaperId, Newspaper newNewspaper) {
        return repository.get(newspaperId)
                .filter(item -> item instanceof Newspaper)
                .map(item -> {
                    Newspaper newspaper = (Newspaper) item;
                    newNewspaper.setId(newspaperId);
                    newNewspaper.setRegistrationDate(newspaper.getRegistrationDate());
                    repository.update(newspaperId, newNewspaper);
                    return newNewspaper;
                })
                .orElseThrow(() -> new NoSuchElementFoundException("Newspaper with id " + newspaperId + " does not exist"));
    }

    public Newspaper delete(Long newspaperId) {
        return repository.get(newspaperId)
                .filter(item -> item instanceof Newspaper)
                .map(item -> {
                    Newspaper newspaper = (Newspaper) item;
                    repository.delete(newspaperId);
                    return newspaper;
                })
                .orElseThrow(() -> new NoSuchElementFoundException("Newspaper with id " + newspaperId + " does not exist"));
    }
}
