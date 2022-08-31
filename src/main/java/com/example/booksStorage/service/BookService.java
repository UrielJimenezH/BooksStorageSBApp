package com.example.booksStorage.service;

import com.example.booksStorage.domain.Item;
import com.example.booksStorage.domain.Book;
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
import java.util.*;

@Service
@Transactional
public class BookService {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private EventManager<Item> eventManager;

    public List<Book> getAll() {
        Query query = entityManager.createNamedQuery("query_find_all_books", Book.class);
        return query.getResultList();
    }

    public Book get(Long id) {
        return Optional.ofNullable(entityManager.find(Book.class, id))
                .orElseThrow(() -> new NoSuchElementFoundException("Book with id " + id + " does not exist"));
    }

    public Book add(Book book) {
        entityManager.persist(book);
        eventManager.notifySubscribers(EventManagerConfig.BOOK_CREATION_EVENT, book);
        return book;
    }

    public Book update(Long id, Book book) {
        Optional<Book> bookFound = Optional.ofNullable(entityManager.find(Book.class, id));

        if (bookFound.isPresent()) {
            book.setId(id);
            book.setHolderId(bookFound.get().getHolderId());
            entityManager.merge(book);
        } else
            throw new NoSuchElementFoundException("Book with id " + id + " does not exist");

        return book;
    }

    public void delete(Long id) {
        Optional<Book> book = Optional.ofNullable(entityManager.find(Book.class, id));

        book.ifPresent((b) -> entityManager.remove(b));
    }

    public Book hold(Long bookId, Long holderId) {
        Optional<User> userFound = Optional.ofNullable(entityManager.find(User.class, holderId));

        if (userFound.isPresent()) {
            Book bookFound = get(bookId);
            if (bookFound.getHolderId() != null && holderId.longValue() != bookFound.getHolderId().longValue())
                throw new ElementAlreadyBeingHoldException();
            else {
                bookFound.setHolderId(holderId);
                return entityManager.merge(bookFound);
            }
        } else
            throw new NoSuchElementFoundException("User with id " + holderId + " does not exist");
    }

    public Book release(Long bookId, Long holderId) {
        Optional<User> userFound = Optional.ofNullable(entityManager.find(User.class, holderId));

        if (userFound.isPresent()) {
            Book bookFound = get(bookId);
            if (bookFound.getHolderId() != null && holderId.longValue() != bookFound.getHolderId().longValue())
                throw new CanNotReleaseException(holderId);
            else {
                bookFound.setHolderId(null);
                return entityManager.merge(bookFound);
            }
        } else
            throw new NoSuchElementFoundException("User with id " + holderId + " does not exist");
    }
}
