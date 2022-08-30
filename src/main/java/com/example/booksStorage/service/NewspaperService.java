package com.example.booksStorage.service;

import com.example.booksStorage.domain.Item;
import com.example.booksStorage.domain.Newspaper;
import com.example.booksStorage.exceptionsHandling.CanNotReleaseException;
import com.example.booksStorage.exceptionsHandling.ElementAlreadyBeingHoldException;
import com.example.booksStorage.exceptionshandling.NoSuchElementFoundException;
import com.example.booksStorage.observer.EventManager;
import com.example.booksStorage.observer.EventManagerConfig;
import com.example.booksStorage.domain.User;
import com.example.booksStorage.repository.NewspaperRepository;
import com.example.booksStorage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class NewspaperService {
    @Autowired
    private NewspaperRepository repository;
    @Autowired
    private UserRepository userRepository;
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

    public Newspaper hold(Long newspaperId, Long holderId) {
        Optional<User> userFound = userRepository.get(holderId);
        if (userFound.isPresent()) {
            Newspaper newspaperFound = get(newspaperId);
            if (newspaperFound.getHolderId() != null && holderId.longValue() != newspaperFound.getHolderId().longValue())
                throw new ElementAlreadyBeingHoldException();
            else
                return repository.hold(newspaperId, holderId)
                        .orElseThrow(() -> new NoSuchElementFoundException("Newspaper with id " + newspaperId + " does not exist"));
        } else
            throw new NoSuchElementFoundException("User with id " + holderId + " does not exist");
    }

    public Newspaper release(Long newspaperId, Long holderId) {
        Optional<User> userFound = userRepository.get(holderId);
        if (userFound.isPresent()) {
            Newspaper newspaperFound = get(newspaperId);
            if (newspaperFound.getHolderId() != null && holderId.longValue() != newspaperFound.getHolderId().longValue())
                throw new CanNotReleaseException(holderId);
            else
                return repository.release(newspaperId)
                        .orElseThrow(() -> new NoSuchElementFoundException("Newspaper with id " + newspaperId + " does not exist"));
        } else
            throw new NoSuchElementFoundException("User with id " + holderId + " does not exist");
    }
}
