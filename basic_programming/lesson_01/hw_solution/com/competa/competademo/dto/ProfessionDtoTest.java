package com.competa.competademo.dto;

import com.competa.competademo.entity.Profession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProfessionServiceImpl is works: ")
class ProfessionDtoTest {

    @Test
    public void testFromProfession() {
        // Создаём объект profession для теста
        Profession profession = new Profession();
        profession.setName("инженер");

        // Вызываем метод from для создания ProfessionDto
        ProfessionDto professionDto = ProfessionDto.from(profession);

        // Проверка, что созданный объект EduLevelDto соответствует ожиданиям
        assertEquals(Long.valueOf(profession.getId()), professionDto.getId());
        assertEquals(profession.getName(), professionDto.getName());
    }

    @Test
    public void testListFromProfession() {
        // Создаём коллекцию объектов Profession для теста
        Profession profession1 = new Profession(1L, "инженер");
        Profession profession2 = new Profession(2L, "конструктор");
        List<Profession> professionsList = Arrays.asList(profession1, profession2);

        // Вызываем метод from для создания списка ProfessionDto
        List<ProfessionDto> professionDtoList = ProfessionDto.from(professionsList);

        // Проверка, что созданный список ProfessionDto соответствует ожиданиям
        assertEquals(professionsList.size(), professionDtoList.size());

        ProfessionDto dto1 = professionDtoList.get(0);
        assertEquals(Long.valueOf(profession1.getId()), dto1.getId());
        assertEquals(profession1.getName(), dto1.getName());

        ProfessionDto dto2 = professionDtoList.get(1);
        assertEquals(Long.valueOf(profession2.getId()), dto2.getId());
        assertEquals(profession2.getName(), dto2.getName());
    }
}
