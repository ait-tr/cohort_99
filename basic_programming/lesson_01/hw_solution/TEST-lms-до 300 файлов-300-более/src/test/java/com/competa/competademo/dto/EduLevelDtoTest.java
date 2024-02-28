package com.competa.competademo.dto;

import com.competa.competademo.entity.Competa;
import com.competa.competademo.entity.EduLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@ExtendWith(MockitoExtension.class)
@DisplayName("EduLevelsServiceImpl is works: ")
class EduLevelDtoTest {

    @Test
    public void testFromEduLevel() {
        // Создаём объект eduLevel для теста
        EduLevel eduLevel = new EduLevel();
        eduLevel.setName("Test EduLevel");

        // Вызываем метод from для создания EduLevelDto
        EduLevelDto eduLevelDto = EduLevelDto.from(eduLevel);

        // Проверка, что созданный объект EduLevelDto соответствует ожиданиям
        assertEquals(Long.valueOf(eduLevel.getId()), eduLevelDto.getId());
        assertEquals(eduLevel.getName(), eduLevelDto.getName());
    }

    @Test
    public void testListFromEduLevels() {
        // Создаём коллекцию объектов EduLevel для теста
        Competa competa = new Competa();
        EduLevel eduLevel1 = new EduLevel(1L, "EduLevel 1",competa);
        EduLevel eduLevel2 = new EduLevel(2L, "EduLevel 2",competa);
        List<EduLevel> eduLevelList = Arrays.asList(eduLevel1, eduLevel2);

        // Вызываем метод from для создания списка EduLevelDto
        List<EduLevelDto> eduLevelDtoList = EduLevelDto.from(eduLevelList);

        // Проверка, что созданный список EduLevelDto соответствует ожиданиям
        assertEquals(eduLevelList.size(), eduLevelDtoList.size());

        EduLevelDto dto1 = eduLevelDtoList.get(0);
        assertEquals(Long.valueOf(eduLevel1.getId()), dto1.getId());
        assertEquals(eduLevel1.getName(), dto1.getName());

        EduLevelDto dto2 = eduLevelDtoList.get(1);
        assertEquals(Long.valueOf(eduLevel2.getId()), dto2.getId());
        assertEquals(eduLevel2.getName(), dto2.getName());
    }
}
