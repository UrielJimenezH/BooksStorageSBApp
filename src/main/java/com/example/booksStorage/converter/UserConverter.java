package com.example.booksStorage.converter;

import com.example.booksStorage.domain.User;
import com.example.booksStorage.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserConverter {
    @Autowired
    private ModelMapper modelMapper;

    public UserDto entityToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    public List<UserDto> entityListToDtoList(List<User> users) {
        return users
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    public User dtoToEntity(UserDto user) {
        return modelMapper.map(user, User.class);
    }

    public List<User> dtoListToEntityList(List<UserDto> users) {
        return users
                .stream()
                .map(this::dtoToEntity)
                .collect(Collectors.toList());
    }
}
