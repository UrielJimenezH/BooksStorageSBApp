package com.example.booksStorage;

import com.example.booksStorage.converter.NewspaperConverter;
import com.example.booksStorage.domain.Newspaper;
import com.example.booksStorage.dto.NewspaperDto;
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
public class NewspaperConverterTest {
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private NewspaperConverter newspaperConverter;
    private final static Newspaper newspaper1 = new Newspaper(
            "Summary 1",
            3,
            LocalDate.now(),
            "Title 1",
            "Publisher"
    );
    private final static Newspaper newspaper2 = new Newspaper(
            "Summary 2",
            3,
            LocalDate.now(),
            "Title 2",
            "Publisher"
    );
    private final static Newspaper newspaper3 = new Newspaper(
            "Summary 3",
            3,
            LocalDate.now(),
            "Title 3",
            "Publisher"
    );
    private final static NewspaperDto newspaperDto1 = new NewspaperDto(
            "Summary 1",
            3,
            LocalDate.now(),
            "Title 1",
            "Publisher"
    );
    private final static NewspaperDto newspaperDto2 = new NewspaperDto(
            "Summary 2",
            3,
            LocalDate.now(),
            "Title 2",
            "Publisher"
    );
    private final static NewspaperDto newspaperDto3 = new NewspaperDto(
            "Summary 3",
            3,
            LocalDate.now(),
            "Title 3",
            "Publisher"
    );

    @Test
    public void entityToDto_ReturnsNewspaperDto() {
        Mockito.when(modelMapper.map(newspaper1, NewspaperDto.class)).thenReturn(newspaperDto1);

        NewspaperDto newNewspaperDto = newspaperConverter.entityToDto(newspaper1);

        assertEquals(newspaper1.getId(), newNewspaperDto.getId());
        assertEquals(newspaper1.getTitle(), newNewspaperDto.getTitle());
        assertEquals(newspaper1.getSummary(), newNewspaperDto.getSummary());
        assertEquals(newspaper1.getNumberOfPages(), newNewspaperDto.getNumberOfPages());
    }

    @Test
    public void entityListToDtoList_ReturnsNewspaperDtoList() {
        Mockito.when(modelMapper.map(newspaper1, NewspaperDto.class)).thenReturn(newspaperDto1);
        Mockito.when(modelMapper.map(newspaper2, NewspaperDto.class)).thenReturn(newspaperDto2);
        Mockito.when(modelMapper.map(newspaper3, NewspaperDto.class)).thenReturn(newspaperDto3);

        List<Newspaper> newspapers = List.of(newspaper1, newspaper2, newspaper3);
        List<NewspaperDto> newNewspaperDtoList = newspaperConverter.entityListToDtoList(newspapers);

        for (int i = 0; i < newspapers.size(); i++) {
            Newspaper newspaper = newspapers.get(i);
            NewspaperDto newNewspaperDto = newNewspaperDtoList.get(i);
            assertEquals(newspaper.getId(), newNewspaperDto.getId());
            assertEquals(newspaper.getTitle(), newNewspaperDto.getTitle());
            assertEquals(newspaper.getSummary(), newNewspaperDto.getSummary());
            assertEquals(newspaper.getNumberOfPages(), newNewspaperDto.getNumberOfPages());
        }
    }


    @Test
    public void dtoToEntity_ReturnsNewspaper() {
        Mockito.when(modelMapper.map(newspaperDto1, Newspaper.class)).thenReturn(newspaper1);

        Newspaper newNewspaper = newspaperConverter.dtoToEntity(newspaperDto1);

        assertEquals(newspaperDto1.getId(), newNewspaper.getId());
        assertEquals(newspaperDto1.getTitle(), newNewspaper.getTitle());
        assertEquals(newspaperDto1.getSummary(), newNewspaper.getSummary());
        assertEquals(newspaperDto1.getNumberOfPages(), newNewspaper.getNumberOfPages());
    }

    @Test
    public void dtoListToEntityList_ReturnsNewspaperList() {
        Mockito.when(modelMapper.map(newspaperDto1, Newspaper.class)).thenReturn(newspaper1);
        Mockito.when(modelMapper.map(newspaperDto2, Newspaper.class)).thenReturn(newspaper2);
        Mockito.when(modelMapper.map(newspaperDto3, Newspaper.class)).thenReturn(newspaper3);

        List<NewspaperDto> newspaperDtoList = List.of(newspaperDto1, newspaperDto2, newspaperDto3);
        List<Newspaper> newNewspapers = newspaperConverter.dtoListToEntityList(newspaperDtoList);

        for (int i = 0; i < newspaperDtoList.size(); i++) {
            NewspaperDto newspaperDto = newspaperDtoList.get(i);
            Newspaper newNewspaper = newNewspapers.get(i);
            assertEquals(newspaperDto.getId(), newNewspaper.getId());
            assertEquals(newspaperDto.getTitle(), newNewspaper.getTitle());
            assertEquals(newspaperDto.getSummary(), newNewspaper.getSummary());
            assertEquals(newspaperDto.getNumberOfPages(), newNewspaper.getNumberOfPages());
        }
    }
}
