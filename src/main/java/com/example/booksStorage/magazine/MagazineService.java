package com.example.booksStorage.magazine;

import com.example.booksStorage.Item;
import com.example.booksStorage.exceptionshandling.NoSuchElementFoundException;
import com.example.booksStorage.observer.EventManager;
import com.example.booksStorage.observer.EventManagerConfig;
import com.example.booksStorage.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MagazineService {
    private final Repository<Long, Item> repository;
    private final EventManager<Item> eventManager;

    @Autowired
    public MagazineService(
            @Qualifier("treeMapRepository") Repository<Long, Item> repository,
            EventManager<Item> eventManager
    ) {
        this.repository = repository;
        this.eventManager = eventManager;
    }

    public List<Magazine> getAll() {
        return repository.getAll()
                .stream()
                .filter(item -> item instanceof Magazine)
                .map(item -> (Magazine) item)
                .collect(Collectors.toList());
    }

    public Magazine get(Long magazineId) {
        return repository.get(magazineId)
                .filter(item -> item instanceof Magazine)
                .map(item -> (Magazine) item)
                .orElseThrow(() -> new NoSuchElementFoundException("Magazine with id " + magazineId + " does not exist"));
    }

    public Magazine add(Magazine magazine) {
        repository.save(magazine.getId(), magazine);
        eventManager.notifySubscribers(EventManagerConfig.MAGAZINE_CREATION_EVENT, magazine);
        return magazine;
    }

    public Magazine update(Long magazineId, Magazine newMagazine) {
        return repository.get(magazineId)
                .filter(item -> item instanceof Magazine)
                .map(item -> {
                    Magazine magazine = (Magazine) item;
                    newMagazine.setId(magazineId);
                    newMagazine.setRegistrationDate(magazine.getRegistrationDate());
                    repository.update(magazineId, newMagazine);
                    return newMagazine;
                })
                .orElseThrow(() -> new NoSuchElementFoundException("Magazine with id " + magazineId + " does not exist"));
    }

    public Magazine delete(Long magazineId) {
        return repository.get(magazineId)
                .filter(item -> item instanceof Magazine)
                .map(item -> {
                    Magazine magazine = (Magazine) item;
                    repository.delete(magazineId);
                    return magazine;
                })
                .orElseThrow(() -> new NoSuchElementFoundException("Magazine with id " + magazineId + " does not exist"));
    }
}
