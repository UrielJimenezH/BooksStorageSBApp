package com.example.booksStorage.letters;

import com.example.booksStorage.Item;
import com.example.booksStorage.exceptions.NoSuchElementFoundException;
import com.example.booksStorage.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LetterService {
    private final Repository<Long, Item> repository;

    @Autowired
    public LetterService(Repository<Long, Item> repository) {
        this.repository = repository;
    }

    public List<Letter> getAll() {
        return repository.getAll()
                .stream()
                .filter(item -> item instanceof Letter)
                .map(item -> (Letter) item)
                .collect(Collectors.toList());
    }

    public Letter get(Long letterId) {
        return repository.get(letterId)
                .filter(item -> item instanceof Letter)
                .map(item -> (Letter) item)
                .orElseThrow(() -> new NoSuchElementFoundException("Letter with id " + letterId + " does not exist"));
    }

    public Letter add(Letter letter) {
        repository.save(letter.getId(), letter);
        return letter;
    }

    public Letter update(Long letterId, Letter newLetter) {
        return repository.get(letterId)
                .filter(item -> item instanceof Letter)
                .map(item -> {
                    Letter letter = (Letter) item;

                    newLetter.setId(letterId);
                    newLetter.setRegistrationDate(letter.getRegistrationDate());
                    repository.update(letterId, newLetter);
                    return letter;
                })
                .orElseThrow(() -> new NoSuchElementFoundException("Letter with id " + letterId + " does not exist"));
    }

    public Letter delete(Long letterId) {
        return repository.get(letterId)
                .filter(item -> item instanceof Letter)
                .map(item -> {
                    Letter letter = (Letter) item;
                    repository.delete(letterId);
                    return letter;
                })
                .orElseThrow(() -> new NoSuchElementFoundException("Letter with id " + letterId + " does not exist"));
    }
}
