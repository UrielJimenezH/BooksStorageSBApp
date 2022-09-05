package com.example.booksStorage.converter;

import com.example.booksStorage.domain.Magazine;
import com.example.booksStorage.dto.MagazineDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MagazineConverter {
    @Autowired
    private ModelMapper modelMapper;

    public MagazineDto entityToDto(Magazine magazine) {
        return modelMapper.map(magazine, MagazineDto.class);
    }

    public List<MagazineDto> entityListToDtoList(List<Magazine> magazines) {
        return magazines
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    public Magazine dtoToEntity(MagazineDto magazine) {
        return modelMapper.map(magazine, Magazine.class);
    }

    public List<Magazine> dtoListToEntityList(List<MagazineDto> magazines) {
        return magazines
                .stream()
                .map(this::dtoToEntity)
                .collect(Collectors.toList());
    }
}
