package com.example.booksStorage.newspaper;

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
}
