package com.example.booksStorage.service;

import com.example.booksStorage.domain.Item;
import com.example.booksStorage.domain.Newspaper;
import com.example.booksStorage.exceptionsHandling.CanNotReleaseException;
import com.example.booksStorage.exceptionsHandling.ElementAlreadyBeingHoldException;
import com.example.booksStorage.exceptionshandling.NoSuchElementFoundException;
import com.example.booksStorage.observer.EventManager;
import com.example.booksStorage.observer.EventManagerConfig;
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
public class NewspaperService {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private EventManager<Item> eventManager;

    public List<Newspaper> getAll() {
        Query query = entityManager.createNamedQuery("query_find_all_newspapers", Newspaper.class);
        return query.getResultList();
    }

    public Newspaper get(Long id) {
        return Optional.ofNullable(entityManager.find(Newspaper.class, id))
                .orElseThrow(() -> new NoSuchElementFoundException("Newspaper with id " + id + " does not exist"));
    }

    public Newspaper add(Newspaper newspaper) {
        entityManager.persist(newspaper);
        eventManager.notifySubscribers(EventManagerConfig.BOOK_CREATION_EVENT, newspaper);
        return newspaper;
    }

    public Newspaper update(Long id, Newspaper newspaper) {
        Optional<Newspaper> newspaperFound = Optional.ofNullable(entityManager.find(Newspaper.class, id));

        if (newspaperFound.isPresent()) {
            newspaper.setId(id);
            newspaper.setHolderId(newspaperFound.get().getHolderId());
            entityManager.merge(newspaper);
        } else
            throw new NoSuchElementFoundException("Newspaper with id " + id + " does not exist");

        return newspaper;
    }

    public void delete(Long id) {
        Optional<Newspaper> newspaper = Optional.ofNullable(entityManager.find(Newspaper.class, id));

        newspaper.ifPresent((n) -> entityManager.remove(n));
    }

    public Newspaper hold(Long newspaperId, Long holderId) {
        Optional<User> userFound = Optional.ofNullable(entityManager.find(User.class, holderId));

        if (userFound.isPresent()) {
            Newspaper newspaperFound = get(newspaperId);
            if (newspaperFound.getHolderId() != null && holderId.longValue() != newspaperFound.getHolderId().longValue())
                throw new ElementAlreadyBeingHoldException();
            else {
                newspaperFound.setHolderId(holderId);
                return entityManager.merge(newspaperFound);
            }
        } else
            throw new NoSuchElementFoundException("User with id " + holderId + " does not exist");
    }

    public Newspaper release(Long newspaperId, Long holderId) {
        Optional<User> userFound = Optional.ofNullable(entityManager.find(User.class, holderId));
        if (userFound.isPresent()) {
            Newspaper newspaperFound = get(newspaperId);
            if (newspaperFound.getHolderId() != null && holderId.longValue() != newspaperFound.getHolderId().longValue())
                throw new CanNotReleaseException(holderId);
            else {
                newspaperFound.setHolderId(null);
                return entityManager.merge(newspaperFound);
            }
        } else
            throw new NoSuchElementFoundException("User with id " + holderId + " does not exist");
    }
}
