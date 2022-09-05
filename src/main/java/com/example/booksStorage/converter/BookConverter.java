package com.example.booksStorage.converter;

import com.example.booksStorage.domain.Book;
import com.example.booksStorage.dto.BookDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookConverter {
    @Autowired
    private ModelMapper modelMapper;

    public BookDto entityToDto(Book book) {
        return modelMapper.map(book, BookDto.class);
    }

    public List<BookDto> entityListToDtoList(List<Book> books) {
        return books
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    public Book dtoToEntity(BookDto book) {
        return modelMapper.map(book, Book.class);
    }

    public List<Book> dtoListToEntityList(List<BookDto> books) {
        return books
                .stream()
                .map(this::dtoToEntity)
                .collect(Collectors.toList());
    }
}
