package com.example.booksStorage.letters;

import com.example.booksStorage.Item;
import com.example.booksStorage.observer.EventManager;
import com.example.booksStorage.observer.EventManagerConfig;
import com.example.booksStorage.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LetterService {
    private final Repository<Long, Item> repository;
    private final EventManager<Item> eventManager;

    @Autowired
    public LetterService(
            Repository<Long, Item> repository,
            EventManager<Item> eventManager
    ) {
        this.repository = repository;
        this.eventManager = eventManager;
    }

    public List<Letter> getAll() {
        return repository.getAll()
                .stream()
                .filter(item -> item instanceof Letter)
                .map(item -> (Letter) item)
                .collect(Collectors.toList());
    }

    public Optional<Letter> get(Long letterId) {
        return repository.get(letterId)
                .filter(item -> item instanceof Letter)
                .map(item -> (Letter) item);
    }

    public Letter add(Letter letter) {
        repository.save(letter.getId(), letter);
        eventManager.notifySubscribers(EventManagerConfig.LETTER_CREATION_EVENT, letter);
        return letter;
    }

    public Optional<Letter> update(Long letterId, Letter newLetter) {
        Optional<Letter> letter = repository.get(letterId)
                .filter(item -> item instanceof Letter)
                .map(item -> (Letter) item);

        if (letter.isEmpty())
            return Optional.empty();

        newLetter.setId(letterId);
        newLetter.setRegistrationDate(letter.get().getRegistrationDate());
        repository.update(letterId, newLetter);
        return Optional.of(newLetter);
    }

    public Optional<Letter> delete(Long letterId) {
        Optional<Letter> letterFound = repository.get(letterId)
                .filter(item -> item instanceof Letter)
                .map(item -> (Letter) item);

        if (letterFound.isPresent())
            repository.delete(letterId);

        return letterFound;
    }
}
