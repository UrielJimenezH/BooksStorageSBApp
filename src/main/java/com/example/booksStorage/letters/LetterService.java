package com.example.booksStorage.letters;

import com.example.booksStorage.Item;
import com.example.booksStorage.observer.EventManager;
import com.example.booksStorage.observer.EventManagerConfig;
import com.example.booksStorage.exceptionshandling.NoSuchElementFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LetterService {
    @Autowired
    private LetterRepository repository;
    @Autowired
    private EventManager<Item> eventManager;

    public List<Letter> getAll() {
        return repository.getAll();
    }

    public Letter get(Long id) {
        return repository.get(id)
                .orElseThrow(() -> new NoSuchElementFoundException("Letter with id " + id + " does not exist"));
    }

    public Letter add(Letter letter) {
        Letter newLetter = repository.save(letter);
        eventManager.notifySubscribers(EventManagerConfig.BOOK_CREATION_EVENT, letter);
        return newLetter;
    }

    public Letter update(Long id, Letter newLetter) {
        newLetter.setId(id);
        Optional<Letter> updatedLetter = repository.update(newLetter);
        return updatedLetter
                .orElseThrow(() -> new NoSuchElementFoundException("Letter with id " + id + " does not exist"));
    }

    public Letter delete(Long id) {
        Letter l = repository.delete(id)
                .orElseThrow(() -> new NoSuchElementFoundException("Letter with id " + id + " does not exist"));

        return l;
    }
}
