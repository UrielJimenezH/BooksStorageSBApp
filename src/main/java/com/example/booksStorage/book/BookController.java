package com.example.booksStorage.book;

import com.example.booksStorage.exceptionsHandling.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService service;

    @Autowired
    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("{bookId}")
    public ResponseEntity<?> getBook(@PathVariable("bookId") Long bookId) {
         return ResponseEntity.ok(service.get(bookId));
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        return ResponseEntity.ok(service.add(book));
    }

    @PutMapping("{bookId}")
    public ResponseEntity<?> updateBook(
            @PathVariable("bookId") Long bookId,
            @RequestBody Book book
    ) {
        return ResponseEntity.ok(service.update(bookId, book));
    }

    @DeleteMapping("{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable("bookId") Long bookId) {
        return ResponseEntity.ok(service.delete(bookId));
    }


    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleIllegalArgumentException(
            IllegalArgumentException exception,
            WebRequest request
    ) {
        return buildErrorResponse(
                exception,
                HttpStatus.BAD_REQUEST,
                request
        );
    }


    public static final String TRACE = "trace";

    @Value("${reflectoring.trace:true}")
    private boolean printStackTrace;

    private ResponseEntity<ErrorResponse> buildErrorResponse(
            Exception exception,
            HttpStatus httpStatus,
            WebRequest request
    ) {
        ErrorResponse errorResponse = new ErrorResponse(
                httpStatus.value(),
                exception.getMessage()
        );

        if(printStackTrace && isTraceOn(request)){
            errorResponse.setStackTrace(
                    Arrays.stream(exception.getStackTrace())
                            .map(StackTraceElement::toString)
                            .collect(Collectors.joining(" --- "))
            );
        }
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    private boolean isTraceOn(WebRequest request) {
        String [] value = request.getParameterValues(TRACE);
        return Objects.nonNull(value)
                && value.length > 0
                && value[0].contentEquals("true");
    }
}
