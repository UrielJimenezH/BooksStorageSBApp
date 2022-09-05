package com.example.booksStorage.converter;

import com.example.booksStorage.domain.Letter;
import com.example.booksStorage.dto.LetterDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LetterConverter {
    @Autowired
    private ModelMapper modelMapper;

    public LetterDto entityToDto(Letter letter) {
        return modelMapper.map(letter, LetterDto.class);
    }

    public List<LetterDto> entityListToDtoList(List<Letter> letters) {
        return letters
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    public Letter dtoToEntity(LetterDto letter) {
        return modelMapper.map(letter, Letter.class);
    }

    public List<Letter> dtoListToEntityList(List<LetterDto> letters) {
        return letters
                .stream()
                .map(this::dtoToEntity)
                .collect(Collectors.toList());
    }
}
