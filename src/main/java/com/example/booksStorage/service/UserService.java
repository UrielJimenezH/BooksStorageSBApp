package com.example.booksStorage.service;

import com.example.booksStorage.domain.User;
import com.example.booksStorage.exceptionshandling.NoSuchElementFoundException;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class UserService {
    @PersistenceContext
    private EntityManager entityManager;

    public List<User> getAll() {
        Query query = entityManager.createNamedQuery("query_find_all_users", User.class);
        return query.getResultList();
    }

    public User get(Long id) {
        return Optional.ofNullable(entityManager.find(User.class, id))
                .orElseThrow(() -> new NoSuchElementFoundException("User with id " + id + " does not exist"));
    }

    public User add(User user) {
        entityManager.persist(user);
        return user;
    }

    public User update(Long id, User user) {
        get(id);

        user.setId(id);
        entityManager.merge(user);
        return user;
    }

    public void delete(Long id) {
        Optional<User> user = Optional.ofNullable(entityManager.find(User.class, id));

        user.ifPresent((u) -> entityManager.remove(u));
    }
}
