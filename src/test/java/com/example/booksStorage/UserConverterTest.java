package com.example.booksStorage;

import com.example.booksStorage.converter.UserConverter;
import com.example.booksStorage.domain.User;
import com.example.booksStorage.dto.UserDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class UserConverterTest {
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private UserConverter userConverter;
    private final static User user1 = new User(
            "Someone 1",
            "Lastname 1",
            "Some address # 134",
            LocalDate.now(),
            "username1",
            "password1"
    );
    private final static User user2 = new User(
            "Someone 2",
            "Lastname 2",
            "Some address # 134",
            LocalDate.now(),
            "username2",
            "password2"
    );
    private final static User user3 = new User(
            "Someone 3",
            "Lastname 3",
            "Some address # 134",
            LocalDate.now(),
            "username3",
            "password3"
    );
    private final static UserDto userDto1 = new UserDto(
            "Someone 1",
            "Lastname 1",
            "Some address # 134",
            LocalDate.now(),
            "username1",
            "password1"
    );
    private final static UserDto userDto2 = new UserDto(
            "Someone 2",
            "Lastname 2",
            "Some address # 134",
            LocalDate.now(),
            "username2",
            "password2"
    );
    private final static UserDto userDto3 = new UserDto(
            "Someone 3",
            "Lastname 3",
            "Some address # 134",
            LocalDate.now(),
            "username3",
            "password3"
    );

    @Test
    public void entityToDto_ReturnsUserDto() {
        Mockito.when(modelMapper.map(user1, UserDto.class)).thenReturn(userDto1);

        UserDto newUserDto = userConverter.entityToDto(user1);

        assertEquals(user1.getId(), newUserDto.getId());
        assertEquals(user1.getName(), newUserDto.getName());
        assertEquals(user1.getLastname(), newUserDto.getLastname());
        assertEquals(user1.getUsername(), newUserDto.getUsername());
    }

    @Test
    public void entityListToDtoList_ReturnsUserDtoList() {
        Mockito.when(modelMapper.map(user1, UserDto.class)).thenReturn(userDto1);
        Mockito.when(modelMapper.map(user2, UserDto.class)).thenReturn(userDto2);
        Mockito.when(modelMapper.map(user3, UserDto.class)).thenReturn(userDto3);

        List<User> users = List.of(user1, user2, user3);
        List<UserDto> newUserDtoList = userConverter.entityListToDtoList(users);

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            UserDto newUserDto = newUserDtoList.get(i);
            assertEquals(user.getId(), newUserDto.getId());
            assertEquals(user.getName(), newUserDto.getName());
            assertEquals(user.getLastname(), newUserDto.getLastname());
            assertEquals(user.getUsername(), newUserDto.getUsername());
        }
    }


    @Test
    public void dtoToEntity_ReturnsUser() {
        Mockito.when(modelMapper.map(userDto1, User.class)).thenReturn(user1);

        User newUser = userConverter.dtoToEntity(userDto1);

        assertEquals(userDto1.getId(), newUser.getId());
        assertEquals(userDto1.getName(), newUser.getName());
        assertEquals(userDto1.getLastname(), newUser.getLastname());
        assertEquals(userDto1.getUsername(), newUser.getUsername());
    }

    @Test
    public void dtoListToEntityList_ReturnsUserList() {
        Mockito.when(modelMapper.map(userDto1, User.class)).thenReturn(user1);
        Mockito.when(modelMapper.map(userDto2, User.class)).thenReturn(user2);
        Mockito.when(modelMapper.map(userDto3, User.class)).thenReturn(user3);

        List<UserDto> userDtoList = List.of(userDto1, userDto2, userDto3);
        List<User> newUsers = userConverter.dtoListToEntityList(userDtoList);

        for (int i = 0; i < userDtoList.size(); i++) {
            UserDto userDto = userDtoList.get(i);
            User newUser = newUsers.get(i);
            assertEquals(userDto.getId(), newUser.getId());
            assertEquals(userDto.getName(), newUser.getName());
            assertEquals(userDto.getLastname(), newUser.getLastname());
            assertEquals(userDto.getUsername(), newUser.getUsername());
        }
    }
}
