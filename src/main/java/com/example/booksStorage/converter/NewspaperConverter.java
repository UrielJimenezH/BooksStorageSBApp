package com.example.booksStorage.converter;

import com.example.booksStorage.domain.Newspaper;
import com.example.booksStorage.dto.NewspaperDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NewspaperConverter {
    @Autowired
    private ModelMapper modelMapper;

    public NewspaperDto entityToDto(Newspaper newspaper) {
        return modelMapper.map(newspaper, NewspaperDto.class);
    }

    public List<NewspaperDto> entityListToDtoList(List<Newspaper> newspapers) {
        return newspapers
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    public Newspaper dtoToEntity(NewspaperDto newspaper) {
        return modelMapper.map(newspaper, Newspaper.class);
    }

    public List<Newspaper> dtoListToEntityList(List<NewspaperDto> newspapers) {
        return newspapers
                .stream()
                .map(this::dtoToEntity)
                .collect(Collectors.toList());
    }
}
