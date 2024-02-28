package com.competa.competademo.service.impl;

import com.competa.competademo.dto.NewSoftSkillDto;
import com.competa.competademo.dto.SoftSkillDto;
import com.competa.competademo.entity.SoftSkill;
import com.competa.competademo.exceptions.RestApiException;
import com.competa.competademo.repository.SoftSkillRepository;
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

/**
 * @author Oleg Karimov
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("SoftSkillsServiceImpl is works: ")
class SoftSkillsServiceImplTest {

    @InjectMocks
    private SoftSkillsServiceImpl softSkillsService;

    @Mock
    private SoftSkillRepository softSkillRepository;

    @DisplayName("getAllSoftSkills method is works: ")
    @Test
    void getAllSoftSkills() {
        // Подготовка данных и моки
        List<SoftSkill> expectedSoftSkills = Arrays.asList(
                new SoftSkill(1L, "Stress-resistant"),
                new SoftSkill(2L, "Teamwork")
        );

        when(softSkillRepository.findAll()).thenReturn(expectedSoftSkills);

        // Выполнение метода, который тестируем
        List<SoftSkillDto> result = softSkillsService.getAllSoftSkills();

        // Проверка результата
        assertNotNull(result);
        assertEquals(2, result.size());

        // Проверка данных
        assertEquals(1L, result.get(0).getId());
        assertEquals("Stress-resistant", result.get(0).getName());
        assertEquals(2L, result.get(1).getId());
        assertEquals("Teamwork", result.get(1).getName());

        // Проверка вызовов методов моков
        verify(softSkillRepository, times(1)).findAll();
    }

    @DisplayName("getSoftSkill method is works: ")
    @Test
    void getSoftSkill() {

        // Подготовка данных и моки
        Long softSkillId = 1L;
        SoftSkill expectedSoftSkill = new SoftSkill(softSkillId, "Stress-resistant");

        // Настройка мок-объекта SoftSkillRepository
        when(softSkillRepository.findById(softSkillId)).thenReturn(Optional.of(expectedSoftSkill));

        // Вызов метода getSoftSkill
        SoftSkillDto result = softSkillsService.getSoftSkill(softSkillId);

        // Проверка результата
        assertNotNull(result);

        // Проверка данных
        assertEquals(1L, result.getId());
        assertEquals("Stress-resistant", result.getName());

        // Проверка вызовов методов моков
        verify(softSkillRepository, times(1)).findById(softSkillId);
    }

    @DisplayName("getSoftSkillOrThrow method is works: ")
    @Test
    void getSoftSkillOr_ThrowException() {

        // Подготовка данных и моки
        Long softSkillId = 100L;

        // Настройка мок-объекта SoftSkillRepository
        when(softSkillRepository.findById(softSkillId)).thenReturn(Optional.empty());

        // Проверка на выброс исключения
        assertThrows("Soft Skill with id <"+softSkillId+"> not found",
                RestApiException.class,
                () -> { softSkillsService.getSoftSkillOrThrow(softSkillId);
        });
    }


    @DisplayName("createSoftSkill method is works: ")
    @Test
    void createSoftSkill() {

        // Подготовка данных и моки
        Long softSkillId = 0L;
        SoftSkill createdSoftSkill = new SoftSkill(softSkillId, "Stress-resistant");

        // Задаем входные данные для теста
        NewSoftSkillDto newSoftSkillDto = new NewSoftSkillDto();
        newSoftSkillDto.setName("Stress-resistant");

        // Когда метод save вызывается на моке репозитория, возвращаем созданный SoftSkill
        when(softSkillRepository.save(any(SoftSkill.class))).thenReturn(createdSoftSkill);

        // Вызываем метод, который мы тестируем
        SoftSkillDto result = softSkillsService.createSoftSkill(newSoftSkillDto);

        // Проверяем, что метод save был вызван с корректными аргументами
        verify(softSkillRepository, times(1)).save(any(SoftSkill.class));

        // Проверяем, что результат метода соответствует ожиданиям
        assertEquals(createdSoftSkill.getId(), result.getId());
        assertEquals("Stress-resistant", result.getName());
    }

    @DisplayName("updateSoftSkill method is works: ")
    @Test
    void updateSoftSkill() {
        // Задаем входные данные для теста
        Long softSkillId = 0L;
        NewSoftSkillDto newSoftSkillDto = new NewSoftSkillDto();
        newSoftSkillDto.setName("Updated Skill");

        // Создаем мок SoftSkill, который должен вернуться из репозитория
        SoftSkill existingSoftSkill = new SoftSkill();
        existingSoftSkill.setName("Original Skill");

        // Когда метод findById вызывается на моке репозитория, возвращаем существующий SoftSkill
        when(softSkillRepository.findById(softSkillId)).thenReturn(Optional.of(existingSoftSkill));

        // Вызываем метод, который мы тестируем
        SoftSkillDto result = softSkillsService.updateSoftSkill(softSkillId, newSoftSkillDto);

        // Проверяем, что метод save вызывается с ожидаемым аргументом
        verify(softSkillRepository, times(1)).save(existingSoftSkill);

        // Проверяем, что результат метода соответствует ожиданиям
        assertEquals(0L, result.getId());
        assertEquals(newSoftSkillDto.getName(), result.getName());
    }

    @DisplayName("deleteSoftSkill method is works: ")
    @Test
    void deleteSoftSkill() {
        // Задаем входные данные для теста
        Long softSkillId = 0L;

        // Создаем мок SoftSkill, который должен вернуться из репозитория
        SoftSkill existingSoftSkill = new SoftSkill();
        existingSoftSkill.setName("To Be Deleted");

        // Когда метод findById вызывается на моке репозитория, возвращаем существующий SoftSkill
        when(softSkillRepository.findById(softSkillId)).thenReturn(Optional.of(existingSoftSkill));

        // Вызываем метод, который мы тестируем
        SoftSkillDto result = softSkillsService.deleteSoftSkill(softSkillId);

        // Проверяем, что метод deleteById вызывается с ожидаемым аргументом
        verify(softSkillRepository, times(1)).findById(softSkillId);
        verify(softSkillRepository, times(1)).deleteById(softSkillId);

        // Проверяем, что результат метода соответствует ожиданиям
        assertEquals(0L, result.getId());
        assertEquals(existingSoftSkill.getName(), result.getName());
    }

    @DisplayName("deleteSoftSkill method is works: ")
    @Test
    void deleteSoftSkill_ThrowException() {
        // Задаем входные данные для теста
        Long softSkillId = 0L;

        // Когда метод findById вызывается на моке репозитория, возвращаем существующий SoftSkill
        when(softSkillRepository.findById(softSkillId)).thenReturn(Optional.empty());

        // Проверка на выброс исключения
        assertThrows("Soft Skill with id <"+softSkillId+"> not found",
                RestApiException.class,
                () -> { softSkillsService.deleteSoftSkill(softSkillId);
                });
    }
}
