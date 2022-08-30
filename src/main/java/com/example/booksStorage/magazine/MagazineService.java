package com.example.booksStorage.magazine;

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
import java.util.List;
import java.util.Optional;

@Service
public class MagazineService {
    @Autowired
    private MagazineRepository repository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EventManager<Item> eventManager;

    public List<Magazine> getAll() {
        return repository.getAll();
    }

    public Magazine get(Long id) {
        return repository.get(id)
                .orElseThrow(() -> new NoSuchElementFoundException("Magazine with id " + id + " does not exist"));
    }

    public Magazine add(Magazine magazine) {
        Magazine newMagazine = repository.save(magazine);
        eventManager.notifySubscribers(EventManagerConfig.BOOK_CREATION_EVENT, magazine);
        return newMagazine;
    }

    public Magazine update(Long id, Magazine newMagazine) {
        newMagazine.setId(id);
        Optional<Magazine> updatedMagazine = repository.update(newMagazine);
        return updatedMagazine
                .orElseThrow(() -> new NoSuchElementFoundException("Magazine with id " + id + " does not exist"));
    }

    public Magazine delete(Long id) {
        return repository.delete(id)
                .orElseThrow(() -> new NoSuchElementFoundException("Magazine with id " + id + " does not exist"));
    }


    public Magazine hold(Long magazineId, Long holderId) {
        Optional<User> userFound = userRepository.get(holderId);
        if (userFound.isPresent()) {
            Magazine magazineFound = get(magazineId);
            if (magazineFound.getHolderId() != null && holderId.longValue() != magazineFound.getHolderId().longValue())
                throw new ElementAlreadyBeingHoldException();
            else
                return repository.hold(magazineId, holderId)
                        .orElseThrow(() -> new NoSuchElementFoundException("Magazine with id " + magazineId + " does not exist"));
        } else
            throw new NoSuchElementFoundException("User with id " + holderId + " does not exist");
    }

    public Magazine release(Long magazineId, Long holderId) {
        Optional<User> userFound = userRepository.get(holderId);
        if (userFound.isPresent()) {
            Magazine magazineFound = get(magazineId);
            if (magazineFound.getHolderId() != null && holderId.longValue() != magazineFound.getHolderId().longValue())
                throw new CanNotReleaseException(holderId);
            else
                return repository.release(magazineId)
                        .orElseThrow(() -> new NoSuchElementFoundException("Magazine with id " + magazineId + " does not exist"));
        } else
            throw new NoSuchElementFoundException("User with id " + holderId + " does not exist");
    }
}
