package com.example.booksStorage.newspaper;

import com.example.booksStorage.Item;
import com.example.booksStorage.exceptionshandling.NoSuchElementFoundException;
import com.example.booksStorage.observer.EventManager;
import com.example.booksStorage.observer.EventManagerConfig;
import com.example.booksStorage.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NewspaperService {
    private final Repository<Long, Item> repository;
    private final EventManager<Item> eventManager;

    @Autowired
    public NewspaperService(
            @Qualifier("treeMapRepository") Repository<Long, Item> repository,
            EventManager<Item> eventManager
    ) {
        this.repository = repository;
        this.eventManager = eventManager;
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
        eventManager.notifySubscribers(EventManagerConfig.NEWSPAPER_CREATION_EVENT, newspaper);
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
