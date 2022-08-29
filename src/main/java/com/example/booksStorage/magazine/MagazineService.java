package com.example.booksStorage.magazine;

import com.example.booksStorage.Item;
import com.example.booksStorage.exceptionshandling.NoSuchElementFoundException;
import com.example.booksStorage.observer.EventManager;
import com.example.booksStorage.observer.EventManagerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MagazineService {
    @Autowired
    private MagazineRepository repository;
    @Autowired
    private EventManager<Item> eventManager;

    public List<Magazine> getAll() {
        return repository.getAll();
    }

    public Magazine get(Long id) {
        return repository.get(id)
                .orElseThrow(() -> new NoSuchElementFoundException("Magazine with id " + id + " does not exist"));
    }

    public Magazine add(Magazine magazine) {
        Magazine newMagazine = repository.save(magazine);
        eventManager.notifySubscribers(EventManagerConfig.BOOK_CREATION_EVENT, magazine);
        return newMagazine;
    }

    public Magazine update(Long id, Magazine newMagazine) {
        newMagazine.setId(id);
        Optional<Magazine> updatedMagazine = repository.update(newMagazine);
        return updatedMagazine
                .orElseThrow(() -> new NoSuchElementFoundException("Magazine with id " + id + " does not exist"));
    }

    public Magazine delete(Long id) {
        return repository.delete(id)
                .orElseThrow(() -> new NoSuchElementFoundException("Magazine with id " + id + " does not exist"));
    }
}
