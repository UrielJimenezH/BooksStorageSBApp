package com.example.booksStorage.newspaper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

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
         Optional<Newspaper> opNewspaper = service.get(newspaperId);

         if (opNewspaper.isPresent())
             return ResponseEntity.ok(opNewspaper.get());

         return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Newspaper with id " + newspaperId + " does not exist");
    }

    @PostMapping
    public ResponseEntity<Newspaper> add(@RequestBody Newspaper newspaper) {
        return ResponseEntity.ok(service.add(newspaper));
    }

    @PutMapping("{newspaperId}")
    public ResponseEntity<?> update(
            @PathVariable("newspaperId") Long newspaperId,
            @RequestBody Newspaper newspaper
    ) {
        Optional<Newspaper> opNewspaper = service.update(newspaperId, newspaper);

        if (opNewspaper.isPresent())
            return ResponseEntity.ok(opNewspaper.get());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Newspaper with id " + newspaperId + " does not exist");
    }

    @DeleteMapping("{newspaperId}")
    public void delete(@PathVariable("newspaperId") Long newspaperId) {
        service.delete(newspaperId);
    }
}
