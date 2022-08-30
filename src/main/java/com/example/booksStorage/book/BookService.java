package com.example.booksStorage.book;

import com.example.booksStorage.Item;
import com.example.booksStorage.exceptionsHandling.CanNotReleaseException;
import com.example.booksStorage.exceptionsHandling.ElementAlreadyBeingHoldException;
import com.example.booksStorage.exceptionshandling.NoSuchElementFoundException;
import com.example.booksStorage.observer.EventManager;
import com.example.booksStorage.observer.EventManagerConfig;
import com.example.booksStorage.user.User;
import com.example.booksStorage.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class BookService {
    @Autowired
    private BookRepository repository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private EventManager<Item> eventManager;

    public List<Book> getAll() {
        return repository.getAll();
    }

    public Book get(Long id) {
        return repository.get(id)
                .orElseThrow(() -> new NoSuchElementFoundException("Book with id " + id + " does not exist"));
    }

    public Book add(Book book) {
        Book newBook = repository.save(book);
        eventManager.notifySubscribers(EventManagerConfig.BOOK_CREATION_EVENT, book);
        return newBook;
    }

    public Book update(Long id, Book newBook) {
        newBook.setId(id);
        Optional<Book> updatedBook = repository.update(newBook);
        return updatedBook
                .orElseThrow(() -> new NoSuchElementFoundException("Book with id " + id + " does not exist"));
    }

    public Book delete(Long id) {
        return repository.delete(id)
                .orElseThrow(() -> new NoSuchElementFoundException("Book with id " + id + " does not exist"));
    }

    public Book hold(Long bookId, Long holderId) {
        Optional<User> userFound = userRepository.get(holderId);
        if (userFound.isPresent()) {
            Book bookFound = get(bookId);
            if (bookFound.getHolderId() != null && holderId.longValue() != bookFound.getHolderId().longValue())
                throw new ElementAlreadyBeingHoldException();
            else
                return repository.hold(bookId, holderId)
                        .orElseThrow(() -> new NoSuchElementFoundException("Book with id " + bookId + " does not exist"));
        } else
            throw new NoSuchElementFoundException("User with id " + holderId + " does not exist");
    }

    public Book release(Long bookId, Long holderId) {
        Optional<User> userFound = userRepository.get(holderId);
        if (userFound.isPresent()) {
            Book bookFound = get(bookId);
            if (bookFound.getHolderId() != null && holderId.longValue() != bookFound.getHolderId().longValue())
                throw new CanNotReleaseException(holderId);
            else
                return repository.release(bookId)
                        .orElseThrow(() -> new NoSuchElementFoundException("Book with id " + bookId + " does not exist"));
        } else
            throw new NoSuchElementFoundException("User with id " + holderId + " does not exist");
    }
}
