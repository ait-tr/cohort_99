package com.competa.competademo.service.impl;

import com.competa.competademo.dto.NewProfessionDto;
import com.competa.competademo.dto.ProfessionDto;
import com.competa.competademo.entity.Profession;
import com.competa.competademo.exceptions.RestApiException;
import com.competa.competademo.repository.ProfessionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProfessionServiceImpl is works: ")
class ProfessionServiceImplTest {

    @InjectMocks
    private ProfessionServiceImpl professionService;

    @Mock
    private ProfessionRepository professionRepository;

    @DisplayName("addEduLevel method is works: ")
    @Test
    void addProfession() {

        // Подготовка данных и моки
        long professionId = 0L;
        Profession addedProfession = new Profession(professionId, "Инженер");

        // Задаем входные данные для теста
        NewProfessionDto newProfessionDto = new NewProfessionDto();
        newProfessionDto.setName("Инженер");

        // Когда метод save вызывается на моке репозитория, возвращаем созданный Profession
        when(professionRepository.save(any(Profession.class))).thenReturn(addedProfession);

        // Вызываем метод, который мы тестируем
        ProfessionDto result = professionService.addProfession(newProfessionDto);

        // Проверяем, что метод save был вызван с корректными аргументами
        verify(professionRepository, times(1)).save(any(Profession.class));

        // Проверяем, что результат метода соответствует ожиданиям
        assertEquals(Long.valueOf(0), result.getId());
        assertEquals(addedProfession.getName(), result.getName());
    }

    @DisplayName("updateProfession method is works: ")
    @Test
    void updateProfession() {
        // Задаем входные данные для теста
        long professionId = 0L;
        NewProfessionDto newProfessionDto = new NewProfessionDto();
        newProfessionDto.setName("Инженер");

        // Создаем мок Profession, который должен вернуться из репозитория
        Profession existingProfession = new Profession();
        existingProfession.setName("Инженер-конструктор");

        // Когда метод findById вызывается на моке репозитория, возвращаем существующий Profession
        when(professionRepository.findById(professionId)).thenReturn(Optional.of(existingProfession));

        // Вызываем метод, который мы тестируем
        ProfessionDto result = professionService.updateProfession(professionId, newProfessionDto);

        // Проверяем, что метод save вызывается с ожидаемым аргументом
        verify(professionRepository, times(1)).save(existingProfession);

        // Проверяем, что результат метода соответствует ожиданиям
        assertEquals(Long.valueOf(professionId), result.getId());
        assertEquals(existingProfession.getName(), result.getName());
    }

    @DisplayName("getAllProfession method is works: ")
    @Test
    void getAllProfession() {
        // Подготовка данных и моки
        List<Profession> expectedProfession = Arrays.asList(
                new Profession(1L, "Инженер"),
                new Profession(2L, "Конструктор")
        );

        when(professionRepository.findAll()).thenReturn(expectedProfession);

        // Выполнение метода, который тестируем
        List<ProfessionDto> result = professionService.getAllProfession();

        // Проверка результата
        assertNotNull(result);
        assertEquals(2, result.size());

        // Проверка данных
        assertEquals(Long.valueOf(expectedProfession.get(0).getId()), result.get(0).getId());
        assertEquals("Инженер", result.get(0).getName());
        assertEquals(Long.valueOf(expectedProfession.get(1).getId()), result.get(1).getId());
        assertEquals("Конструктор", result.get(1).getName());

        // Проверка вызовов методов моков
        verify(professionRepository, times(1)).findAll();
    }

    @DisplayName("getByIdProfession method is works: ")
    @Test
    void getByIdProfession() {

        // Подготовка данных и моки
        long professionId = 1L;
        Profession expectedProfession = new Profession(professionId, "Инженер");

        // Настройка мок-объекта ProfessionRepository
        when(professionRepository.findById(professionId)).thenReturn(Optional.of(expectedProfession));

        // Вызов метода getByIdProfession
        ProfessionDto result = professionService.getByIdProfession(professionId);

        // Проверка результата
        assertNotNull(result);

        // Проверка данных
        assertEquals(Long.valueOf(professionId), result.getId());
        assertEquals("Инженер", result.getName());

        // Проверка вызовов методов моков
        verify(professionRepository, times(1)).findById(professionId);
    }

    @DisplayName("deleteProfession method is works: ")
    @Test
    void deleteProfession() {
        // Задаем входные данные для теста
        long professionId = 1L;

        // Создаем мок Profession, который должен вернуться из репозитория
        Profession existingProfession = new Profession(professionId, "Инженер");

        // Когда метод findById вызывается на моке репозитория, возвращаем существующий Profession
        when(professionRepository.findById(professionId)).thenReturn(Optional.of(existingProfession));

        // Вызываем метод, который мы тестируем
        ProfessionDto result = professionService.deleteProfession(professionId);

        // Проверяем, что метод deleteByIdProfession вызывается с ожидаемым аргументом
        verify(professionRepository, times(1)).findById(professionId);
        verify(professionRepository, times(1)).deleteById(professionId);

        // Проверяем, что результат метода соответствует ожиданиям
        assertEquals(Long.valueOf(existingProfession.getId()), result.getId());
        assertEquals(existingProfession.getName(), result.getName());
    }

    @DisplayName("getProfessionOrElseThrow method is works: ")
    @Test
    void getProfessionOrElseThrowException() {

        // Подготовка данных и моки
        long professionId = 100L;

        // Настройка мок-объекта ProfessionRepository
        when(professionRepository.findById(professionId)).thenReturn(Optional.empty());

        // Проверка на выброс исключения
        assertThrows("Profession with id <" + professionId + "> not found",
                RestApiException.class,
                () -> {
                    professionService.getProfessionOrElseThrow(professionId);
                });
    }

}
