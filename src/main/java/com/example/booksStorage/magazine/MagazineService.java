package com.example.booksStorage.magazine;

import com.example.booksStorage.Item;
import com.example.booksStorage.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MagazineService {
    private final Repository<Long, Item> repository;

    @Autowired
    public MagazineService(Repository<Long, Item> repository) {
        this.repository = repository;
    }

    public List<Magazine> getAll() {
        return repository.getAll()
                .stream()
                .filter(item -> item instanceof Magazine)
                .map(item -> (Magazine) item)
                .collect(Collectors.toList());
    }

    public Optional<Magazine> get(Long bookId) {
        return repository.get(bookId)
                .filter(item -> item instanceof Magazine)
                .map(item -> (Magazine) item);
    }

    public Magazine add(Magazine book) {
        repository.save(book.getId(), book);
        return book;
    }

    public Optional<Magazine> update(Long bookId, Magazine newMagazine) {
        Optional<Magazine> book = repository.get(bookId)
                .filter(item -> item instanceof Magazine)
                .map(item -> (Magazine) item);

        if (book.isEmpty())
            return Optional.empty();

        newMagazine.setId(bookId);
        newMagazine.setRegistrationDate(book.get().getRegistrationDate());
        repository.update(bookId, newMagazine);
        return Optional.of(newMagazine);
    }

    public void delete(Long bookId) {
        repository.delete(bookId);
    }
}
