package com.example.booksStorage.letters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/letters")
public class LetterController {
    private final LetterService service;

    @Autowired
    public LetterController(LetterService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Letter>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("{letterId}")
    public ResponseEntity<?> get(@PathVariable("letterId") Long letterId) {
         Optional<Letter> opLetter = service.get(letterId);

         if (opLetter.isPresent())
             return ResponseEntity.ok(opLetter.get());

         return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Letter with id " + letterId + " does not exist");
    }

    @PostMapping
    public ResponseEntity<Letter> add(@RequestBody Letter letter) {
        return ResponseEntity.ok(service.add(letter));
    }

    @PutMapping("{letterId}")
    public ResponseEntity<?> update(
            @PathVariable("letterId") Long letterId,
            @RequestBody Letter letter
    ) {
        Optional<Letter> opLetter = service.update(letterId, letter);

        if (opLetter.isPresent())
            return ResponseEntity.ok(opLetter.get());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Letter with id " + letterId + " does not exist");
    }

    @DeleteMapping("{letterId}")
    public void delete(@PathVariable("letterId") Long letterId) {
        service.delete(letterId);
    }
}
