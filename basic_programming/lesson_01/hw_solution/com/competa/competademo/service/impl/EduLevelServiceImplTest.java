package com.competa.competademo.service.impl;

import com.competa.competademo.dto.EduLevelDto;
import com.competa.competademo.dto.NewEduLevelDto;
import com.competa.competademo.entity.Competa;
import com.competa.competademo.entity.EduLevel;
import com.competa.competademo.exceptions.RestApiException;
import com.competa.competademo.repository.EduLevelRepository;
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
@DisplayName("EduLevelServiceImpl is works: ")
class EduLevelServiceImplTest {

    @InjectMocks
    private EduLevelServiceImpl eduLevelService;

    @Mock
    private EduLevelRepository eduLevelRepository;

    @DisplayName("addEduLevel method is works: ")
    @Test
    void addEduLevel() {

        // Подготовка данных и моки
        Competa competa = new Competa();
        long eduLevelId = 0L;
        EduLevel addedEduLevel = new EduLevel(eduLevelId, "Начальное",competa);

        // Задаем входные данные для теста
        NewEduLevelDto newEduLevel = new NewEduLevelDto();
        newEduLevel.setName("Начальное");

        // Когда метод save вызывается на моке репозитория, возвращаем созданный EduLevel
        when(eduLevelRepository.save(any(EduLevel.class))).thenReturn(addedEduLevel);

        // Вызываем метод, который мы тестируем
        EduLevelDto result = eduLevelService.addEduLevel(newEduLevel);

        // Проверяем, что метод save был вызван с корректными аргументами
        verify(eduLevelRepository, times(1)).save(any(EduLevel.class));

        // Проверяем, что результат метода соответствует ожиданиям
        assertEquals(Long.valueOf(eduLevelId), result.getId());
        assertEquals(addedEduLevel.getName(), result.getName());
    }

    @DisplayName("updateEduLevel method is works: ")
    @Test
    void updateEduLevel() {
        // Задаем входные данные для теста
        long eduLevelId = 0L;
        NewEduLevelDto newEduLevelDto = new NewEduLevelDto();
        newEduLevelDto.setName("Высшее");

        // Создаем мок EduLevel, который должен вернуться из репозитория
        EduLevel existingEduLevel = new EduLevel();
        existingEduLevel.setName("Начальное");

        // Когда метод findById вызывается на моке репозитория, возвращаем существующий EduLevel
        when(eduLevelRepository.findById(eduLevelId)).thenReturn(Optional.of(existingEduLevel));

        // Вызываем метод, который мы тестируем
        EduLevelDto result = eduLevelService.updateEduLevel(eduLevelId, newEduLevelDto);

        // Проверяем, что метод save вызывается с ожидаемым аргументом
        verify(eduLevelRepository, times(1)).save(existingEduLevel);

        // Проверяем, что результат метода соответствует ожиданиям
        assertEquals(Long.valueOf(eduLevelId), result.getId());
        assertEquals(existingEduLevel.getName(), result.getName());
    }

    @DisplayName("getAllEduLevel method is works: ")
    @Test
    void getAllEduLevel() {
        // Подготовка данных и моки
        Competa competa = new Competa();
        List<EduLevel> expectedEduLevel = Arrays.asList(
                new EduLevel(1L, "Stress-resistant",competa),
                new EduLevel(2L, "Teamwork",competa)
        );

        when(eduLevelRepository.findAll()).thenReturn(expectedEduLevel);

        // Выполнение метода, который тестируем
        List<EduLevelDto> result = eduLevelService.getAllEduLevel();

        // Проверка результата
        assertNotNull(result);
        assertEquals(2, result.size());

        // Проверка данных
        assertEquals(Long.valueOf(expectedEduLevel.get(0).getId()), result.get(0).getId());
        assertEquals("Stress-resistant", result.get(0).getName());
        assertEquals(Long.valueOf(expectedEduLevel.get(1).getId()), result.get(1).getId());
        assertEquals("Teamwork", result.get(1).getName());

        // Проверка вызовов методов моков
        verify(eduLevelRepository, times(1)).findAll();
    }

    @DisplayName("getByIdEduLevel method is works: ")
    @Test
    void getByIdEduLevel() {

        // Подготовка данных и моки
        long eduLevelId = 1L;
        Competa competa = new Competa();
        EduLevel expectedEduLevel = new EduLevel(eduLevelId, "Stress-resistant",competa);

        // Настройка мок-объекта EduLevelRepository
        when(eduLevelRepository.findById(eduLevelId)).thenReturn(Optional.of(expectedEduLevel));

        // Вызов метода getByIdEduLevel
        EduLevelDto result = eduLevelService.getByIdEduLevel(eduLevelId);

        // Проверка результата
        assertNotNull(result);

        // Проверка данных
        assertEquals(Long.valueOf(eduLevelId), result.getId());
        assertEquals("Stress-resistant", result.getName());

        // Проверка вызовов методов моков
        verify(eduLevelRepository, times(1)).findById(eduLevelId);
    }

    @DisplayName("deleteEduLevel method is works: ")
    @Test
    void deleteEduLevel() {
        // Задаем входные данные для теста
        long eduLevelId = 1L;
        Competa competa = new Competa();

        // Создаем мок EduLevel, который должен вернуться из репозитория
        EduLevel existingEduLevel = new EduLevel(eduLevelId, "To Be Deleted",competa);

        // Когда метод findById вызывается на моке репозитория, возвращаем существующий EduLevel
        when(eduLevelRepository.findById(eduLevelId)).thenReturn(Optional.of(existingEduLevel));

        // Вызываем метод, который мы тестируем
        EduLevelDto result = eduLevelService.deleteEduLevel(eduLevelId);

        // Проверяем, что метод deleteByIdEduLevel вызывается с ожидаемым аргументом
        verify(eduLevelRepository, times(1)).findById(eduLevelId);
        verify(eduLevelRepository, times(1)).deleteById(eduLevelId);

        // Проверяем, что результат метода соответствует ожиданиям
        assertEquals(Long.valueOf(existingEduLevel.getId()), result.getId());
        assertEquals(existingEduLevel.getName(), result.getName());
    }

    @DisplayName("getEduLevelOrElseThrow method is works: ")
    @Test
    void getEduLevelOrElseThrowException() {

        // Подготовка данных и моки
        long eduLevelId = 100L;

        // Настройка мок-объекта EduLevelRepository
        when(eduLevelRepository.findById(eduLevelId)).thenReturn(Optional.empty());

        // Проверка на выброс исключения
        assertThrows("Soft Skill with id <" + eduLevelId + "> not found",
                RestApiException.class,
                () -> {
                    eduLevelService.getEduLevelOrElseThrow(eduLevelId);
                });
    }

}
