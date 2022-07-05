package com.example.booksStorage.magazine;

import com.example.booksStorage.Item;
import com.example.booksStorage.observer.EventManager;
import com.example.booksStorage.observer.Subscriber;
import com.example.booksStorage.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MagazineService {
    private final Repository<Long, Item> repository;
    private final EventManager<Item> eventManager;
    private final String CREATION_EVENT = "magazineCreated";

    @Autowired
    public MagazineService(
            Repository<Long, Item> repository,
            EventManager<Item> eventManager,
            Subscriber<Item> subscriber
    ) {
        this.repository = repository;
        this.eventManager = eventManager;
        eventManager.subscribe(CREATION_EVENT, subscriber);
    }

    public List<Magazine> getAll() {
        return repository.getAll()
                .stream()
                .filter(item -> item instanceof Magazine)
                .map(item -> (Magazine) item)
                .collect(Collectors.toList());
    }

    public Optional<Magazine> get(Long magazineId) {
        return repository.get(magazineId)
                .filter(item -> item instanceof Magazine)
                .map(item -> (Magazine) item);
    }

    public Magazine add(Magazine magazine) {
        repository.save(magazine.getId(), magazine);
        eventManager.notifySubscribers(CREATION_EVENT, magazine);
        return magazine;
    }

    public Optional<Magazine> update(Long magazineId, Magazine newMagazine) {
        Optional<Magazine> magazine = repository.get(magazineId)
                .filter(item -> item instanceof Magazine)
                .map(item -> (Magazine) item);

        if (magazine.isEmpty())
            return Optional.empty();

        newMagazine.setId(magazineId);
        newMagazine.setRegistrationDate(magazine.get().getRegistrationDate());
        repository.update(magazineId, newMagazine);
        return Optional.of(newMagazine);
    }

    public Optional<Magazine> delete(Long magazineId) {
        Optional<Magazine> magazineFound = repository.get(magazineId)
                .filter(item -> item instanceof Magazine)
                .map(item -> (Magazine) item);

        if (magazineFound.isPresent())
            repository.delete(magazineId);

        return magazineFound;
    }
}
