package com.example.booksStorage.newspaper;

import com.example.booksStorage.Item;
import com.example.booksStorage.exceptionshandling.NoSuchElementFoundException;
import com.example.booksStorage.observer.EventManager;
import com.example.booksStorage.observer.EventManagerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class NewspaperService {
    @Autowired
    private NewspaperRepository repository;
    @Autowired
    private EventManager<Item> eventManager;

    public List<Newspaper> getAll() {
        return repository.getAll();
    }

    public Newspaper get(Long id) {
        return repository.get(id)
                .orElseThrow(() -> new NoSuchElementFoundException("Newspaper with id " + id + " does not exist"));
    }

    public Newspaper add(Newspaper newspaper) {
        Newspaper newNewspaper = repository.save(newspaper);
        eventManager.notifySubscribers(EventManagerConfig.BOOK_CREATION_EVENT, newspaper);
        return newNewspaper;
    }

    public Newspaper update(Long id, Newspaper newNewspaper) {
        newNewspaper.setId(id);
        Optional<Newspaper> updatedNewspaper = repository.update(newNewspaper);
        return updatedNewspaper
                .orElseThrow(() -> new NoSuchElementFoundException("Newspaper with id " + id + " does not exist"));
    }

    public Newspaper delete(Long id) {
        return repository.delete(id)
                .orElseThrow(() -> new NoSuchElementFoundException("Newspaper with id " + id + " does not exist"));
    }
}
