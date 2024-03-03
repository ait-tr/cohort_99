package com.competa.competademo.service.impl;

import com.competa.competademo.dto.DriverLicenceDto;
import com.competa.competademo.dto.NewDriverLicenceDto;
import com.competa.competademo.entity.Competa;
import com.competa.competademo.entity.DriverLicence;
import com.competa.competademo.exceptions.RestApiException;
import com.competa.competademo.repository.DriverLicenceRepository;
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
@DisplayName("DriverLicenceServiceImpl is works: ")
class DriverLicenceServiceImplTest {

    @InjectMocks
    private DriverLicenceServiceImpl driverLicenceService;

    @Mock
    private DriverLicenceRepository driverLicenceRepository;

    @DisplayName("addDriverLicence method is works: ")
    @Test
    void addDriverLicence() {

        // Подготовка данных и моки
        Competa competa = new Competa();
        long driverLicenceId = 0L;
        DriverLicence addedDriverLicence = new DriverLicence(driverLicenceId, "A",competa);

        // Задаем входные данные для теста
        NewDriverLicenceDto newDriverLicence = new NewDriverLicenceDto();
        newDriverLicence.setName("A");

        // Когда метод save вызывается на моке репозитория, возвращаем созданный DriverLicence
        when(driverLicenceRepository.save(any(DriverLicence.class))).thenReturn(addedDriverLicence);

        // Вызываем метод, который мы тестируем
        DriverLicenceDto result = driverLicenceService.addDriverLicence(newDriverLicence);

        // Проверяем, что метод save был вызван с корректными аргументами
        verify(driverLicenceRepository, times(1)).save(any(DriverLicence.class));

        // Проверяем, что результат метода соответствует ожиданиям
        assertEquals(Long.valueOf(driverLicenceId), result.getId());
        assertEquals(addedDriverLicence.getName(), result.getName());
    }

    @DisplayName("updateDriverLicence method is works: ")
    @Test
    void updateDriverLicence() {
        // Задаем входные данные для теста
        long driverLicenceId = 0L;
        NewDriverLicenceDto newDriverLicenceDto = new NewDriverLicenceDto();
        newDriverLicenceDto.setName("B");

        // Создаем мок DriverLicence, который должен вернуться из репозитория
        DriverLicence existingDriverLicence = new DriverLicence();
        existingDriverLicence.setName("A");

        // Когда метод findById вызывается на моке репозитория, возвращаем существующий DriverLicence
        when(driverLicenceRepository.findById(driverLicenceId)).thenReturn(Optional.of(existingDriverLicence));

        // Вызываем метод, который мы тестируем
        DriverLicenceDto result = driverLicenceService.updateDriverLicence(driverLicenceId, newDriverLicenceDto);

        // Проверяем, что метод save вызывается с ожидаемым аргументом
        verify(driverLicenceRepository, times(1)).save(existingDriverLicence);

        // Проверяем, что результат метода соответствует ожиданиям
        assertEquals(Long.valueOf(driverLicenceId), result.getId());
        assertEquals(existingDriverLicence.getName(), result.getName());
    }

    @DisplayName("getAllDriverLicence method is works: ")
    @Test
    void getAllDriverLicence() {
        // Подготовка данных и моки
        Competa competa = new Competa();
        List<DriverLicence> expectedDriverLicence = Arrays.asList(
                new DriverLicence(1L, "A",competa),
                new DriverLicence(2L, "B",competa)
        );

        when(driverLicenceRepository.findAll()).thenReturn(expectedDriverLicence);

        // Выполнение метода, который тестируем
        List<DriverLicenceDto> result = driverLicenceService.getAllDriverLicence();

        // Проверка результата
        assertNotNull(result);
        assertEquals(2, result.size());

        // Проверка данных
        assertEquals(Long.valueOf(expectedDriverLicence.get(0).getId()), result.get(0).getId());
        assertEquals("A", result.get(0).getName());
        assertEquals(Long.valueOf(expectedDriverLicence.get(1).getId()), result.get(1).getId());
        assertEquals("B", result.get(1).getName());

        // Проверка вызовов методов моков
        verify(driverLicenceRepository, times(1)).findAll();
    }

    @DisplayName("getByIdDriverLicence method is works: ")
    @Test
    void getByIdDriverLicence() {

        // Подготовка данных и моки
        Competa competa = new Competa();
        long driverLicenceId = 1L;
        DriverLicence expectedDriverLicence = new DriverLicence(driverLicenceId, "A",competa);

        // Настройка мок-объекта DriverLicenceRepository
        when(driverLicenceRepository.findById(driverLicenceId)).thenReturn(Optional.of(expectedDriverLicence));

        // Вызов метода getByIdDriverLicence
        DriverLicenceDto result = driverLicenceService.getByIdDriverLicence(driverLicenceId);

        // Проверка результата
        assertNotNull(result);

        // Проверка данных
        assertEquals(Long.valueOf(driverLicenceId), result.getId());
        assertEquals("A", result.getName());

        // Проверка вызовов методов моков
        verify(driverLicenceRepository, times(1)).findById(driverLicenceId);
    }

    @DisplayName("deleteDriverLicence method is works: ")
    @Test
    void deleteDriverLicence() {
        // Задаем входные данные для теста
        Competa competa = new Competa();
        long driverLicenceId = 1L;

        // Создаем мок DriverLicence, который должен вернуться из репозитория
        DriverLicence existingDriverLicence = new DriverLicence(driverLicenceId, "A",competa);

        // Когда метод findById вызывается на моке репозитория, возвращаем существующий DriverLicence
        when(driverLicenceRepository.findById(driverLicenceId)).thenReturn(Optional.of(existingDriverLicence));

        // Вызываем метод, который мы тестируем
        DriverLicenceDto result = driverLicenceService.deleteDriverLicence(driverLicenceId);

        // Проверяем, что метод deleteByIdDriverLicence вызывается с ожидаемым аргументом
        verify(driverLicenceRepository, times(1)).findById(driverLicenceId);
        verify(driverLicenceRepository, times(1)).deleteById(driverLicenceId);

        // Проверяем, что результат метода соответствует ожиданиям
        assertEquals(Long.valueOf(existingDriverLicence.getId()), result.getId());
        assertEquals(existingDriverLicence.getName(), result.getName());
    }

    @DisplayName("getDriverLicenceOrElseThrow method is works: ")
    @Test
    void getDriverLicenceOrElseThrowException() {

        // Подготовка данных и моки
        long driverLicenceId = 100L;

        // Настройка мок-объекта DriverLicenceRepository
        when(driverLicenceRepository.findById(driverLicenceId)).thenReturn(Optional.empty());

        // Проверка на выброс исключения
        assertThrows("Driver Licence with id <" + driverLicenceId + "> not found",
                RestApiException.class,
                () -> {
                    driverLicenceService.getDriverLicenceOrElseThrow(driverLicenceId);
                });
    }

}
