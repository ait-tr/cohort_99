package com.competa.competademo.service.impl;

import com.competa.competademo.dto.HardSkillDto;
import com.competa.competademo.dto.NewHardSkillDto;
import com.competa.competademo.entity.Competa;
import com.competa.competademo.entity.HardSkill;
import com.competa.competademo.exceptions.RestApiException;
import com.competa.competademo.repository.HardSkillRepository;
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
@DisplayName("HardSkillsServiceImpl is works: ")
public class HardSkillsServiceImplTest {
    @InjectMocks
    private HardSkillsServiceImpl hardSkillsService;

    @Mock
    private HardSkillRepository hardSkillRepository;

    @DisplayName("getAllHardSkills method is works: ")
    @Test
    void getAllHardSkills() {
        // Подготовка данных и моки
        Competa competa = new Competa();
        List<HardSkill> expectedHardSkills = Arrays.asList(
                new HardSkill(1L, "Stress-resistant",competa),
                new HardSkill(2L, "Teamwork",competa)
        );

        when(hardSkillRepository.findAll()).thenReturn(expectedHardSkills);

        // Выполнение метода, который тестируем
        List<HardSkillDto> result = hardSkillsService.getAllHardSkills();

        // Проверка результата
        assertNotNull(result);
        assertEquals(2, result.size());

        // Проверка данных
        assertEquals(1L, result.get(0).getId());
        assertEquals("Stress-resistant", result.get(0).getName());
        assertEquals(2L, result.get(1).getId());
        assertEquals("Teamwork", result.get(1).getName());

        // Проверка вызовов методов моков
        verify(hardSkillRepository, times(1)).findAll();
    }

    @DisplayName("getHardSkill method is works: ")
    @Test
    void getHardSkill() {

        // Подготовка данных и моки
        Long hardSkillId = 1L;
        Competa competa = new Competa();
        HardSkill expectedHardSkill = new HardSkill(hardSkillId, "Stress-resistant",competa);

        // Настройка мок-объекта HardSkillRepository
        when(hardSkillRepository.findById(hardSkillId)).thenReturn(Optional.of(expectedHardSkill));

        // Вызов метода getHardSkill
        HardSkillDto result = hardSkillsService.getHardSkillById(hardSkillId);

        // Проверка результата
        assertNotNull(result);

        // Проверка данных
        assertEquals(1L, result.getId());
        assertEquals("Stress-resistant", result.getName());

        // Проверка вызовов методов моков
        verify(hardSkillRepository, times(1)).findById(hardSkillId);
    }

    @DisplayName("getHardSkillOrThrow method is works: ")
    @Test
    void getHardSkillOr_ThrowException() {

        // Подготовка данных и моки
        Long hardSkillId = 100L;

        // Настройка мок-объекта HardSkillRepository
        when(hardSkillRepository.findById(hardSkillId)).thenReturn(Optional.empty());

        // Проверка на выброс исключения
        assertThrows("Hard Skill with id <"+hardSkillId+"> not found",
                RestApiException.class,
                () -> { hardSkillsService.getHardSkillOrThrow(hardSkillId);
                });
    }


    @DisplayName("createHardSkill method is works: ")
    @Test
    void createHardSkill() {

        // Подготовка данных и моки
        Long hardSkillId = 0L;
        Competa competa = new Competa();
        HardSkill createdHardSkill = new HardSkill(hardSkillId, "Stress-resistant",competa);

        // Задаем входные данные для теста
        NewHardSkillDto newHardSkillDto = new NewHardSkillDto();
        newHardSkillDto.setName("Stress-resistant");

        // Когда метод save вызывается на моке репозитория, возвращаем созданный HardSkill
        when(hardSkillRepository.save(any(HardSkill.class))).thenReturn(createdHardSkill);

        // Вызываем метод, который мы тестируем
        HardSkillDto result = hardSkillsService.createHardSkill(newHardSkillDto);

        // Проверяем, что метод save был вызван с корректными аргументами
        verify(hardSkillRepository, times(1)).save(any(HardSkill.class));

        // Проверяем, что результат метода соответствует ожиданиям
        assertEquals(createdHardSkill.getId(), result.getId());
        assertEquals("Stress-resistant", result.getName());
    }

    @DisplayName("updateHardSkill method is works: ")
    @Test
    void updateHardSkill() {
        // Задаем входные данные для теста
        Long hardSkillId = 0L;
        NewHardSkillDto newHardSkillDto = new NewHardSkillDto();
        newHardSkillDto.setName("Updated Skill");

        // Создаем мок HardSkill, который должен вернуться из репозитория
        HardSkill existingHardSkill = new HardSkill();
        existingHardSkill.setName("Original Skill");

        // Когда метод findById вызывается на моке репозитория, возвращаем существующий HardSkill
        when(hardSkillRepository.findById(hardSkillId)).thenReturn(Optional.of(existingHardSkill));

        // Вызываем метод, который мы тестируем
        HardSkillDto result = hardSkillsService.updateHardSkill(hardSkillId, newHardSkillDto);

        // Проверяем, что метод save вызывается с ожидаемым аргументом
        verify(hardSkillRepository, times(1)).save(existingHardSkill);

        // Проверяем, что результат метода соответствует ожиданиям
        assertEquals(0L, result.getId());
        assertEquals(newHardSkillDto.getName(), result.getName());
    }

    @DisplayName("deleteHardSkill method is works: ")
    @Test
    void deleteHardSkill() {
        // Задаем входные данные для теста
        Long hardSkillId = 0L;

        // Создаем мок HardSkill, который должен вернуться из репозитория
        HardSkill existingHardSkill = new HardSkill();
        existingHardSkill.setName("To Be Deleted");

        // Когда метод findById вызывается на моке репозитория, возвращаем существующий HardSkill
        when(hardSkillRepository.findById(hardSkillId)).thenReturn(Optional.of(existingHardSkill));

        // Вызываем метод, который мы тестируем
        HardSkillDto result = hardSkillsService.deleteHardSkill(hardSkillId);

        // Проверяем, что метод deleteById вызывается с ожидаемым аргументом
        verify(hardSkillRepository, times(1)).findById(hardSkillId);
        verify(hardSkillRepository, times(1)).deleteById(hardSkillId);

        // Проверяем, что результат метода соответствует ожиданиям
        assertEquals(0L, result.getId());
        assertEquals(existingHardSkill.getName(), result.getName());
    }

    @DisplayName("deleteHardSkill method is works: ")
    @Test
    void deleteHardSkill_ThrowException() {
        // Задаем входные данные для теста
        Long hardSkillId = 0L;

        // Когда метод findById вызывается на моке репозитория, возвращаем существующий HardSkill
        when(hardSkillRepository.findById(hardSkillId)).thenReturn(Optional.empty());

        // Проверка на выброс исключения
        assertThrows("Hard Skill with id <"+hardSkillId+"> not found",
                RestApiException.class,
                () -> { hardSkillsService.deleteHardSkill(hardSkillId);
                });
    }
}
