package com.example.booksStorage;

import com.example.booksStorage.converter.LetterConverter;
import com.example.booksStorage.domain.Letter;
import com.example.booksStorage.dto.LetterDto;
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
public class LetterConverterTest {
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private LetterConverter letterConverter;
    private final static Letter letter1 = new Letter(
            "Summary 1",
            3,
            LocalDate.now(),
            "Author 1"
    );
    private final static Letter letter2 = new Letter(
            "Summary 2",
            3,
            LocalDate.now(),
            "Author 2"
    );
    private final static Letter letter3 = new Letter(
            "Summary 3",
            3,
            LocalDate.now(),
            "Author 3"
    );
    private final static LetterDto letterDto1 = new LetterDto(
            "Summary 1",
            3,
            LocalDate.now(),
            "Author 1"
    );
    private final static LetterDto letterDto2 = new LetterDto(
            "Summary 2",
            3,
            LocalDate.now(),
            "Author 2"
    );
    private final static LetterDto letterDto3 = new LetterDto(
            "Summary 3",
            3,
            LocalDate.now(),
            "Author 3"
    );

    @Test
    public void entityToDto_ReturnsLetterDto() {
        Mockito.when(modelMapper.map(letter1, LetterDto.class)).thenReturn(letterDto1);

        LetterDto newLetterDto = letterConverter.entityToDto(letter1);

        assertEquals(letter1.getId(), newLetterDto.getId());
        assertEquals(letter1.getSummary(), newLetterDto.getSummary());
        assertEquals(letter1.getNumberOfPages(), newLetterDto.getNumberOfPages());
    }

    @Test
    public void entityListToDtoList_ReturnsLetterDtoList() {
        Mockito.when(modelMapper.map(letter1, LetterDto.class)).thenReturn(letterDto1);
        Mockito.when(modelMapper.map(letter2, LetterDto.class)).thenReturn(letterDto2);
        Mockito.when(modelMapper.map(letter3, LetterDto.class)).thenReturn(letterDto3);

        List<Letter> letters = List.of(letter1, letter2, letter3);
        List<LetterDto> newLetterDtoList = letterConverter.entityListToDtoList(letters);

        for (int i = 0; i < letters.size(); i++) {
            Letter letter = letters.get(i);
            LetterDto newLetterDto = newLetterDtoList.get(i);
            assertEquals(letter.getId(), newLetterDto.getId());
            assertEquals(letter.getSummary(), newLetterDto.getSummary());
            assertEquals(letter.getNumberOfPages(), newLetterDto.getNumberOfPages());
        }
    }


    @Test
    public void dtoToEntity_ReturnsLetter() {
        Mockito.when(modelMapper.map(letterDto1, Letter.class)).thenReturn(letter1);

        Letter newLetter = letterConverter.dtoToEntity(letterDto1);

        assertEquals(letterDto1.getId(), newLetter.getId());
        assertEquals(letterDto1.getSummary(), newLetter.getSummary());
        assertEquals(letterDto1.getNumberOfPages(), newLetter.getNumberOfPages());
    }

    @Test
    public void dtoListToEntityList_ReturnsLetterList() {
        Mockito.when(modelMapper.map(letterDto1, Letter.class)).thenReturn(letter1);
        Mockito.when(modelMapper.map(letterDto2, Letter.class)).thenReturn(letter2);
        Mockito.when(modelMapper.map(letterDto3, Letter.class)).thenReturn(letter3);

        List<LetterDto> letterDtoList = List.of(letterDto1, letterDto2, letterDto3);
        List<Letter> newLetters = letterConverter.dtoListToEntityList(letterDtoList);

        for (int i = 0; i < letterDtoList.size(); i++) {
            LetterDto letterDto = letterDtoList.get(i);
            Letter newLetter = newLetters.get(i);
            assertEquals(letterDto.getId(), newLetter.getId());
            assertEquals(letterDto.getSummary(), newLetter.getSummary());
            assertEquals(letterDto.getNumberOfPages(), newLetter.getNumberOfPages());
        }
    }
}
