package com.example.booksStorage.service;

import com.example.booksStorage.domain.Item;
import com.example.booksStorage.domain.Magazine;
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
public class MagazineService {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private EventManager<Item> eventManager;

    public List<Magazine> getAll() {
        Query query = entityManager.createNamedQuery("query_find_all_magazines", Magazine.class);
        return query.getResultList();
    }

    public Magazine get(Long id) {
        return Optional.ofNullable(entityManager.find(Magazine.class, id))
                .orElseThrow(() -> new NoSuchElementFoundException("Magazine with id " + id + " does not exist"));
    }

    public Magazine add(Magazine magazine) {
        entityManager.persist(magazine);
        eventManager.notifySubscribers(EventManagerConfig.BOOK_CREATION_EVENT, magazine);
        return magazine;
    }

    public Magazine update(Long id, Magazine magazine) {
        Optional<Magazine> magazineFound = Optional.ofNullable(entityManager.find(Magazine.class, id));

        if (magazineFound.isPresent()) {
            magazine.setId(id);
            magazine.setHolderId(magazineFound.get().getHolderId());
            entityManager.merge(magazine);
        } else
               throw new NoSuchElementFoundException("Magazine with id " + id + " does not exist");

        return magazine;
    }

    public void delete(Long id) {
        Optional<Magazine> magazine = Optional.ofNullable(entityManager.find(Magazine.class, id));

        magazine.ifPresent((m) -> entityManager.remove(m));
    }


    public Magazine hold(Long magazineId, Long holderId) {
        Optional<User> userFound = Optional.ofNullable(entityManager.find(User.class, holderId));

        if (userFound.isPresent()) {
            Magazine magazineFound = get(magazineId);
            if (magazineFound.getHolderId() != null && holderId.longValue() != magazineFound.getHolderId().longValue())
                throw new ElementAlreadyBeingHoldException();
            else {
                magazineFound.setHolderId(holderId);
                return entityManager.merge(magazineFound);
            }
        } else
            throw new NoSuchElementFoundException("User with id " + holderId + " does not exist");
    }

    public Magazine release(Long magazineId, Long holderId) {
        Optional<User> userFound = Optional.ofNullable(entityManager.find(User.class, holderId));

        if (userFound.isPresent()) {
            Magazine magazineFound = get(magazineId);
            if (magazineFound.getHolderId() != null && holderId.longValue() != magazineFound.getHolderId().longValue())
                throw new CanNotReleaseException(holderId);
            else {
                magazineFound.setHolderId(null);
                return entityManager.merge(magazineFound);
            }
        } else
            throw new NoSuchElementFoundException("User with id " + holderId + " does not exist");
    }
}
