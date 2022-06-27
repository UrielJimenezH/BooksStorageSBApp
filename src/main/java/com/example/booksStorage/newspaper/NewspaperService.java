package com.example.booksStorage.newspaper;

import com.example.booksStorage.Item;
import com.example.booksStorage.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
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

    public Optional<Newspaper> get(Long newspaperId) {
        return repository.get(newspaperId)
                .filter(item -> item instanceof Newspaper)
                .map(item -> (Newspaper) item);
    }

    public Newspaper add(Newspaper newspaper) {
        repository.save(newspaper.getId(), newspaper);
        return newspaper;
    }

    public Optional<Newspaper> update(Long newspaperId, Newspaper newNewspaper) {
        Optional<Newspaper> newspaper = repository.get(newspaperId)
                .filter(item -> item instanceof Newspaper)
                .map(item -> (Newspaper) item);

        if (newspaper.isEmpty())
            return Optional.empty();

        newNewspaper.setId(newspaperId);
        newNewspaper.setRegistrationDate(newspaper.get().getRegistrationDate());
        repository.update(newspaperId, newNewspaper);
        return Optional.of(newNewspaper);
    }

    public void delete(Long newspaperId) {
        repository.delete(newspaperId);
    }
}
