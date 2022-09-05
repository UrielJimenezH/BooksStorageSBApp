package com.example.booksStorage;

import com.example.booksStorage.converter.BookConverter;
import com.example.booksStorage.domain.Book;
import com.example.booksStorage.dto.BookDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class BookConverterTest {
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private BookConverter bookConverter;
    private final static Book book1 = new Book(
            "Summary 1",
            3,
            LocalDate.now(),
            "Title 1",
            "Author",
            "Publisher",
            "Edition"
    );
    private final static Book book2 = new Book(
            "Summary 2",
            3,
            LocalDate.now(),
            "Title 2",
            "Author",
            "Publisher",
            "Edition"
    );
    private final static Book book3 = new Book(
            "Summary 3",
            3,
            LocalDate.now(),
            "Title 3",
            "Author",
            "Publisher",
            "Edition"
    );
    private final static BookDto bookDto1 = new BookDto(
            "Summary 1",
            3,
            LocalDate.now(),
            "Title 1",
            "Author",
            "Publisher",
            "Edition"
    );
    private final static BookDto bookDto2 = new BookDto(
            "Summary 2",
            3,
            LocalDate.now(),
            "Title 2",
            "Author",
            "Publisher",
            "Edition"
    );
    private final static BookDto bookDto3 = new BookDto(
            "Summary 3",
            3,
            LocalDate.now(),
            "Title 3",
            "Author",
            "Publisher",
            "Edition"
    );

    @Test
    public void entityToDto_ReturnsBookDto() {
        Mockito.when(modelMapper.map(book1, BookDto.class)).thenReturn(bookDto1);

        BookDto newBookDto = bookConverter.entityToDto(book1);

        assertEquals(book1.getId(), newBookDto.getId());
        assertEquals(book1.getTitle(), newBookDto.getTitle());
        assertEquals(book1.getSummary(), newBookDto.getSummary());
        assertEquals(book1.getNumberOfPages(), newBookDto.getNumberOfPages());
    }

    @Test
    public void entityListToDtoList_ReturnsBookDtoList() {
        Mockito.when(modelMapper.map(book1, BookDto.class)).thenReturn(bookDto1);
        Mockito.when(modelMapper.map(book2, BookDto.class)).thenReturn(bookDto2);
        Mockito.when(modelMapper.map(book3, BookDto.class)).thenReturn(bookDto3);

        List<Book> books = List.of(book1, book2, book3);
        List<BookDto> newBookDtoList = bookConverter.entityListToDtoList(books);

        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            BookDto newBookDto = newBookDtoList.get(i);
            assertEquals(book.getId(), newBookDto.getId());
            assertEquals(book.getTitle(), newBookDto.getTitle());
            assertEquals(book.getSummary(), newBookDto.getSummary());
            assertEquals(book.getNumberOfPages(), newBookDto.getNumberOfPages());
        }
    }


    @Test
    public void dtoToEntity_ReturnsBook() {
        Mockito.when(modelMapper.map(bookDto1, Book.class)).thenReturn(book1);

        Book newBook = bookConverter.dtoToEntity(bookDto1);

        assertEquals(bookDto1.getId(), newBook.getId());
        assertEquals(bookDto1.getTitle(), newBook.getTitle());
        assertEquals(bookDto1.getSummary(), newBook.getSummary());
        assertEquals(bookDto1.getNumberOfPages(), newBook.getNumberOfPages());
    }

    @Test
    public void dtoListToEntityList_ReturnsBookList() {
        Mockito.when(modelMapper.map(bookDto1, Book.class)).thenReturn(book1);
        Mockito.when(modelMapper.map(bookDto2, Book.class)).thenReturn(book2);
        Mockito.when(modelMapper.map(bookDto3, Book.class)).thenReturn(book3);

        List<BookDto> bookDtoList = List.of(bookDto1, bookDto2, bookDto3);
        List<Book> newBooks = bookConverter.dtoListToEntityList(bookDtoList);

        for (int i = 0; i < bookDtoList.size(); i++) {
            BookDto bookDto = bookDtoList.get(i);
            Book newBook = newBooks.get(i);
            assertEquals(bookDto.getId(), newBook.getId());
            assertEquals(bookDto.getTitle(), newBook.getTitle());
            assertEquals(bookDto.getSummary(), newBook.getSummary());
            assertEquals(bookDto.getNumberOfPages(), newBook.getNumberOfPages());
        }
    }
}
