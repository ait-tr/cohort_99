package com.competa.competademo.service.impl;

import com.competa.competademo.dto.IndustryDto;
import com.competa.competademo.dto.NewIndustryDto;
import com.competa.competademo.entity.Industry;
import com.competa.competademo.exceptions.RestApiException;
import com.competa.competademo.repository.IndustryRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IndustryServiceImplTest {
    @InjectMocks
    private IndustryServiceImpl industryService;

    @Mock
    private IndustryRepository industryRepository;

    @DisplayName("addIndustry method is works: ")
    @Test
    void addIndustry() {

        // Подготовка данных и моки
        long industryId = 0L;
        Industry addedIndustry = new Industry(industryId, "Education");

        // Задаем входные данные для теста
        NewIndustryDto newIndustry = new NewIndustryDto();
        newIndustry.setName("Education");

        // Когда метод save вызывается на моке репозитория, возвращаем созданный EduLevel
        when(industryRepository.save(any(Industry.class))).thenReturn(addedIndustry);

        // Вызываем метод, который мы тестируем
        IndustryDto result = industryService.addIndustry(newIndustry);

        // Проверяем, что метод save был вызван с корректными аргументами
        verify(industryRepository, times(1)).save(any(Industry.class));

        // Проверяем, что результат метода соответствует ожиданиям
        assertEquals(Long.valueOf(industryId), result.getId());
        assertEquals(addedIndustry.getName(), result.getName());
    }

    @DisplayName("updateIndustry method is works: ")
    @Test
    void updateIndustry() {
        // Задаем входные данные для теста
        long industryId = 0L;
        NewIndustryDto newIndustryDto = new NewIndustryDto();
        newIndustryDto.setName("Education");

        // Создаем мок EduLevel, который должен вернуться из репозитория
        Industry existingIndustry = new Industry();
        existingIndustry.setName("Education");

        // Когда метод findById вызывается на моке репозитория, возвращаем существующий EduLevel
        when(industryRepository.findById(industryId)).thenReturn(Optional.of(existingIndustry));

        // Вызываем метод, который мы тестируем
        IndustryDto result = industryService.updateIndustry(industryId, newIndustryDto);

        // Проверяем, что метод save вызывается с ожидаемым аргументом
        verify(industryRepository, times(1)).save(existingIndustry);

        // Проверяем, что результат метода соответствует ожиданиям
        assertEquals(Long.valueOf(industryId), result.getId());
        assertEquals(existingIndustry.getName(), result.getName());
    }

    @DisplayName("getAllIndustry method is works: ")
    @Test
    void getAllIndustry() {
        // Подготовка данных и моки
        List<Industry> expectedIndustry = Arrays.asList(
                new Industry(1L, "Education"),
                new Industry(2L, "Information technologies")
        );

        when(industryRepository.findAll()).thenReturn(expectedIndustry);

        // Выполнение метода, который тестируем
        List<IndustryDto> result = industryService.getAllIndustry();

        // Проверка результата
        assertNotNull(result);
        assertEquals(2, result.size());

        // Проверка данных
        assertEquals(Long.valueOf(expectedIndustry.get(0).getId()), result.get(0).getId());
        assertEquals("Education", result.get(0).getName());
        assertEquals(Long.valueOf(expectedIndustry.get(1).getId()), result.get(1).getId());
        assertEquals("Information technologies", result.get(1).getName());

        // Проверка вызовов методов моков
        verify(industryRepository, times(1)).findAll();
    }

    @DisplayName("getByIdIndustry method is works: ")
    @Test
    void getByIdIndustry() {

        // Подготовка данных и моки
        long industryId = 1L;
        Industry expectedIndustry = new Industry(industryId, "Education");

        // Настройка мок-объекта IndustryRepository
        when(industryRepository.findById(industryId)).thenReturn(Optional.of(expectedIndustry));

        // Вызов метода getByIdIndustry
        IndustryDto result = industryService.getByIdIndustry(industryId);

        // Проверка результата
        assertNotNull(result);

        // Проверка данных
        assertEquals(Long.valueOf(industryId), result.getId());
        assertEquals("Education", result.getName());

        // Проверка вызовов методов моков
        verify(industryRepository, times(1)).findById(industryId);
    }

    @DisplayName("deleteIndustry method is works: ")
    @Test
    void deleteIndustry() {
        // Задаем входные данные для теста
        long industryId = 1L;

        // Создаем мок Industry, который должен вернуться из репозитория
        Industry existingIndustry = new Industry(industryId, "To Be Deleted");

        // Когда метод findById вызывается на моке репозитория, возвращаем существующий EduLevel
        when(industryRepository.findById(industryId)).thenReturn(Optional.of(existingIndustry));

        // Вызываем метод, который мы тестируем
        IndustryDto result = industryService.deleteIndustry(industryId);

        // Проверяем, что метод deleteByIdIndustry вызывается с ожидаемым аргументом
        verify(industryRepository, times(1)).findById(industryId);
        verify(industryRepository, times(1)).deleteById(industryId);

        // Проверяем, что результат метода соответствует ожиданиям
        assertEquals(Long.valueOf(existingIndustry.getId()), result.getId());
        assertEquals(existingIndustry.getName(), result.getName());
    }

    @DisplayName("getIndustryOrElseThrow method is works: ")
    @Test
    void getIndustryOrElseThrowException() {

        // Подготовка данных и моки
        long industryId = 100L;

        // Настройка мок-объекта EduLevelRepository
        when(industryRepository.findById(industryId)).thenReturn(Optional.empty());

        // Проверка на выброс исключения
        assertThrows("Industry with id <" + industryId + "> not found",
                RestApiException.class,
                () -> {
                    industryService.getIndustryOrElseThrow(industryId);
                });
    }
}
