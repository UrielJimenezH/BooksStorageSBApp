package com.example.booksStorage.service;

import com.example.booksStorage.domain.Item;
import com.example.booksStorage.domain.Letter;
import com.example.booksStorage.exceptionsHandling.CanNotReleaseException;
import com.example.booksStorage.exceptionsHandling.ElementAlreadyBeingHoldException;
import com.example.booksStorage.observer.EventManager;
import com.example.booksStorage.observer.EventManagerConfig;
import com.example.booksStorage.exceptionshandling.NoSuchElementFoundException;
import com.example.booksStorage.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LetterService {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private EventManager<Item> eventManager;

    public List<Letter> getAll() {
        Query query = entityManager.createNamedQuery("query_find_all_letters", Letter.class);
        return query.getResultList();
    }

    public Letter get(Long id) {
        return Optional.ofNullable(entityManager.find(Letter.class, id))
                .orElseThrow(() -> new NoSuchElementFoundException("Letter with id " + id + " does not exist"));
    }

    public Letter add(Letter letter) {
        entityManager.persist(letter);
        eventManager.notifySubscribers(EventManagerConfig.BOOK_CREATION_EVENT, letter);
        return letter;
    }

    public Letter update(Long id, Letter letter) {
        Optional<Letter> letterFound = Optional.ofNullable(entityManager.find(Letter.class, id));

        if (letterFound.isPresent()) {
            letter.setId(id);
            letter.setHolderId(letterFound.get().getHolderId());
            entityManager.merge(letter);
        } else
            throw new NoSuchElementFoundException("Letter with id " + id + " does not exist");

        return letter;
    }

    public void delete(Long id) {
        Optional<Letter> letter = Optional.ofNullable(entityManager.find(Letter.class, id));

        letter.ifPresent((l) -> entityManager.remove(l));
    }

    public Letter hold(Long letterId, Long holderId) {
        Optional<User> userFound = Optional.ofNullable(entityManager.find(User.class, holderId));

        if (userFound.isPresent()) {
            Letter letterFound = get(letterId);
            if (letterFound.getHolderId() != null && holderId.longValue() != letterFound.getHolderId().longValue())
                throw new ElementAlreadyBeingHoldException();
            else {
                letterFound.setHolderId(holderId);
                return entityManager.merge(letterFound);
            }
        } else
            throw new NoSuchElementFoundException("User with id " + holderId + " does not exist");
    }

    public Letter release(Long letterId, Long holderId) {
        Optional<User> userFound = Optional.ofNullable(entityManager.find(User.class, holderId));

        if (userFound.isPresent()) {
            Letter letterFound = get(letterId);
            if (letterFound.getHolderId() != null && holderId.longValue() != letterFound.getHolderId().longValue())
                throw new CanNotReleaseException(holderId);
            else {
                letterFound.setHolderId(null);
                return entityManager.merge(letterFound);
            }
        } else
            throw new NoSuchElementFoundException("User with id " + holderId + " does not exist");
    }
}
