package com.example.booksStorage.controller;

import com.example.booksStorage.domain.Newspaper;
import com.example.booksStorage.domain.Holder;
import com.example.booksStorage.service.NewspaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/newspapers")
public class NewspaperController {
    private final NewspaperService service;

    @Autowired
    public NewspaperController(NewspaperService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Newspaper>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("{newspaperId}")
    public ResponseEntity<?> get(@PathVariable("newspaperId") Long newspaperId) {
        return ResponseEntity.ok(service.get(newspaperId));
    }

    @PostMapping
    public ResponseEntity<Newspaper> add(@RequestBody Newspaper newspaper) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.add(newspaper));
    }

    @PutMapping("{newspaperId}")
    public ResponseEntity<?> update(
            @PathVariable("newspaperId") Long newspaperId,
            @RequestBody Newspaper newspaper
    ) {
        return ResponseEntity.ok(service.update(newspaperId, newspaper));
    }

    @DeleteMapping("{newspaperId}")
    public ResponseEntity<?> delete(@PathVariable("newspaperId") Long newspaperId) {
        return ResponseEntity.ok(service.delete(newspaperId));
    }

    @PostMapping("{newspaperId}/hold")
    public ResponseEntity<Newspaper> holdNewspaper(@PathVariable("newspaperId") Long newspaperId, @RequestBody Holder user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.hold(newspaperId, user.getHolderId()));
    }

    @PostMapping("{newspaperId}/release")
    public ResponseEntity<Newspaper> releaseNewspaper(@PathVariable("newspaperId") Long newspaperId, @RequestBody Holder user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.release(newspaperId, user.getHolderId()));
    }
}