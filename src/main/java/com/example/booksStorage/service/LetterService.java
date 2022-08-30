package com.example.booksStorage.service;

import com.example.booksStorage.domain.Item;
import com.example.booksStorage.domain.Letter;
import com.example.booksStorage.exceptionsHandling.CanNotReleaseException;
import com.example.booksStorage.exceptionsHandling.ElementAlreadyBeingHoldException;
import com.example.booksStorage.observer.EventManager;
import com.example.booksStorage.observer.EventManagerConfig;
import com.example.booksStorage.exceptionshandling.NoSuchElementFoundException;
import com.example.booksStorage.domain.User;
import com.example.booksStorage.repository.LetterRepository;
import com.example.booksStorage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LetterService {
    @Autowired
    private LetterRepository repository;
    @Autowired
    private UserRepository userRepository;
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
        return repository.delete(id)
                .orElseThrow(() -> new NoSuchElementFoundException("Letter with id " + id + " does not exist"));
    }

    public Letter hold(Long letterId, Long holderId) {
        Optional<User> userFound = userRepository.get(holderId);
        if (userFound.isPresent()) {
            Letter letterFound = get(letterId);
            if (letterFound.getHolderId() != null && holderId.longValue() != letterFound.getHolderId().longValue())
                throw new ElementAlreadyBeingHoldException();
            else
                return repository.hold(letterId, holderId)
                        .orElseThrow(() -> new NoSuchElementFoundException("Letter with id " + letterId + " does not exist"));
        } else
            throw new NoSuchElementFoundException("User with id " + holderId + " does not exist");
    }

    public Letter release(Long letterId, Long holderId) {
        Optional<User> userFound = userRepository.get(holderId);
        if (userFound.isPresent()) {
            Letter letterFound = get(letterId);
            if (letterFound.getHolderId() != null && holderId.longValue() != letterFound.getHolderId().longValue())
                throw new CanNotReleaseException(holderId);
            else
                return repository.release(letterId)
                        .orElseThrow(() -> new NoSuchElementFoundException("Letter with id " + letterId + " does not exist"));
        } else
            throw new NoSuchElementFoundException("User with id " + holderId + " does not exist");
    }
}
