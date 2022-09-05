package com.example.booksStorage;

import com.example.booksStorage.converter.MagazineConverter;
import com.example.booksStorage.domain.Magazine;
import com.example.booksStorage.dto.MagazineDto;
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
public class MagazineConverterTest {
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private MagazineConverter magazineConverter;
    private final static Magazine magazine1 = new Magazine(
            "Summary 1",
            3,
            LocalDate.now(),
            "Title 1",
            "Publisher"
    );
    private final static Magazine magazine2 = new Magazine(
            "Summary 2",
            3,
            LocalDate.now(),
            "Title 2",
            "Publisher"
    );
    private final static Magazine magazine3 = new Magazine(
            "Summary 3",
            3,
            LocalDate.now(),
            "Title 3",
            "Publisher"
    );
    private final static MagazineDto magazineDto1 = new MagazineDto(
            "Summary 1",
            3,
            LocalDate.now(),
            "Title 1",
            "Publisher"
    );
    private final static MagazineDto magazineDto2 = new MagazineDto(
            "Summary 2",
            3,
            LocalDate.now(),
            "Title 2",
            "Publisher"
    );
    private final static MagazineDto magazineDto3 = new MagazineDto(
            "Summary 3",
            3,
            LocalDate.now(),
            "Title 3",
            "Publisher"
    );

    @Test
    public void entityToDto_ReturnsMagazineDto() {
        Mockito.when(modelMapper.map(magazine1, MagazineDto.class)).thenReturn(magazineDto1);

        MagazineDto newMagazineDto = magazineConverter.entityToDto(magazine1);

        assertEquals(magazine1.getId(), newMagazineDto.getId());
        assertEquals(magazine1.getTitle(), newMagazineDto.getTitle());
        assertEquals(magazine1.getSummary(), newMagazineDto.getSummary());
        assertEquals(magazine1.getNumberOfPages(), newMagazineDto.getNumberOfPages());
    }

    @Test
    public void entityListToDtoList_ReturnsMagazineDtoList() {
        Mockito.when(modelMapper.map(magazine1, MagazineDto.class)).thenReturn(magazineDto1);
        Mockito.when(modelMapper.map(magazine2, MagazineDto.class)).thenReturn(magazineDto2);
        Mockito.when(modelMapper.map(magazine3, MagazineDto.class)).thenReturn(magazineDto3);

        List<Magazine> magazines = List.of(magazine1, magazine2, magazine3);
        List<MagazineDto> newMagazineDtoList = magazineConverter.entityListToDtoList(magazines);

        for (int i = 0; i < magazines.size(); i++) {
            Magazine magazine = magazines.get(i);
            MagazineDto newMagazineDto = newMagazineDtoList.get(i);
            assertEquals(magazine.getId(), newMagazineDto.getId());
            assertEquals(magazine.getTitle(), newMagazineDto.getTitle());
            assertEquals(magazine.getSummary(), newMagazineDto.getSummary());
            assertEquals(magazine.getNumberOfPages(), newMagazineDto.getNumberOfPages());
        }
    }


    @Test
    public void dtoToEntity_ReturnsMagazine() {
        Mockito.when(modelMapper.map(magazineDto1, Magazine.class)).thenReturn(magazine1);

        Magazine newMagazine = magazineConverter.dtoToEntity(magazineDto1);

        assertEquals(magazineDto1.getId(), newMagazine.getId());
        assertEquals(magazineDto1.getTitle(), newMagazine.getTitle());
        assertEquals(magazineDto1.getSummary(), newMagazine.getSummary());
        assertEquals(magazineDto1.getNumberOfPages(), newMagazine.getNumberOfPages());
    }

    @Test
    public void dtoListToEntityList_ReturnsMagazineList() {
        Mockito.when(modelMapper.map(magazineDto1, Magazine.class)).thenReturn(magazine1);
        Mockito.when(modelMapper.map(magazineDto2, Magazine.class)).thenReturn(magazine2);
        Mockito.when(modelMapper.map(magazineDto3, Magazine.class)).thenReturn(magazine3);

        List<MagazineDto> magazineDtoList = List.of(magazineDto1, magazineDto2, magazineDto3);
        List<Magazine> newMagazines = magazineConverter.dtoListToEntityList(magazineDtoList);

        for (int i = 0; i < magazineDtoList.size(); i++) {
            MagazineDto magazineDto = magazineDtoList.get(i);
            Magazine newMagazine = newMagazines.get(i);
            assertEquals(magazineDto.getId(), newMagazine.getId());
            assertEquals(magazineDto.getTitle(), newMagazine.getTitle());
            assertEquals(magazineDto.getSummary(), newMagazine.getSummary());
            assertEquals(magazineDto.getNumberOfPages(), newMagazine.getNumberOfPages());
        }
    }
}
