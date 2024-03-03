package com.competa.competademo.service.impl;

import com.competa.competademo.dto.JobTitleDto;
import com.competa.competademo.dto.NewJobTitleDto;
import com.competa.competademo.entity.Competa;
import com.competa.competademo.entity.JobTitle;
import com.competa.competademo.exceptions.RestApiException;
import com.competa.competademo.repository.JobTitleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("JobTitleServiceImpl is works: ")
class JobTitleServiceImplTest {

    @InjectMocks
    private JobTitleServiceImpl jobTitleService;

    @Mock
    private JobTitleRepository jobTitleRepository;

    @DisplayName("addJobTitle method is works: ")
    @Test
    void addJobTitle() {

        // Подготовка данных и моки
        long jobTitleId = 0L;
        JobTitle addedJobTitle = new JobTitle(jobTitleId, "JobTitle", new ArrayList<Competa>());

        // Задаем входные данные для теста
        NewJobTitleDto newJobTitleDto = new NewJobTitleDto();
        newJobTitleDto.setName("JobTitle");

        // Когда метод save вызывается на моке репозитория, возвращаем созданный JobTitle
        when(jobTitleRepository.save(any(JobTitle.class))).thenReturn(addedJobTitle);

        // Вызываем метод, который мы тестируем
        JobTitleDto result = jobTitleService.addJobTitle(newJobTitleDto);

        // Проверяем, что метод save был вызван с корректными аргументами
        verify(jobTitleRepository, times(1)).save(any(JobTitle.class));

        // Проверяем, что результат метода соответствует ожиданиям
        assertEquals(Long.valueOf(jobTitleId), result.getId());
        assertEquals(addedJobTitle.getName(), result.getName());
    }

    @DisplayName("updateJobTitle method is works: ")
    @Test
    void updateJobTitle() {
        // Задаем входные данные для теста
        long jobTitleId = 0L;
        NewJobTitleDto newJobTitleDto = new NewJobTitleDto();
        newJobTitleDto.setName("JobTitle");

        // Создаем мок JobTitle, который должен вернуться из репозитория
        JobTitle existingJobTitle = new JobTitle();
        existingJobTitle.setName("Job_Title");

        // Когда метод findById вызывается на моке репозитория, возвращаем существующий JobTitle
        when(jobTitleRepository.findById(jobTitleId)).thenReturn(Optional.of(existingJobTitle));

        // Вызываем метод, который мы тестируем
        JobTitleDto result = jobTitleService.updateJobTitle(jobTitleId, newJobTitleDto);

        // Проверяем, что метод save вызывается с ожидаемым аргументом
        verify(jobTitleRepository, times(1)).save(existingJobTitle);

        // Проверяем, что результат метода соответствует ожиданиям
        assertEquals(Long.valueOf(jobTitleId), result.getId());
        assertEquals(existingJobTitle.getName(), result.getName());
    }

    @DisplayName("getAllJobTitle method is works: ")
    @Test
    void getAllJobTitle() {
        // Подготовка данных и моки
        List<JobTitle> expectedJobTitle = Arrays.asList(
                new JobTitle(1L, "JobTitle 1", new ArrayList<Competa>()),
                new JobTitle(2L, "JobTitle 2", new ArrayList<Competa>())
        );

        when(jobTitleRepository.findAll()).thenReturn(expectedJobTitle);

        // Выполнение метода, который тестируем
        List<JobTitleDto> result = jobTitleService.getAllJobTitle();

        // Проверка результата
        assertNotNull(result);
        assertEquals(2, result.size());

        // Проверка данных
        assertEquals(Long.valueOf(expectedJobTitle.get(0).getId()), result.get(0).getId());
        assertEquals("JobTitle 1", result.get(0).getName());
        assertEquals(Long.valueOf(expectedJobTitle.get(1).getId()), result.get(1).getId());
        assertEquals("JobTitle 2", result.get(1).getName());

        // Проверка вызовов методов моков
        verify(jobTitleRepository, times(1)).findAll();
    }

    @DisplayName("getByIdJobTitle method is works: ")
    @Test
    void getByIdJobTitle() {

        // Подготовка данных и моки
        long jobTitleId = 1L;
        JobTitle expectedJobTitle = new JobTitle(jobTitleId, "JobTitle", new ArrayList<Competa>());

        // Настройка мок-объекта JobTitleRepository
        when(jobTitleRepository.findById(jobTitleId)).thenReturn(Optional.of(expectedJobTitle));

        // Вызов метода getByIdJobTitle
        JobTitleDto result = jobTitleService.getByIdJobTitle(jobTitleId);

        // Проверка результата
        assertNotNull(result);

        // Проверка данных
        assertEquals(Long.valueOf(jobTitleId), result.getId());
        assertEquals("JobTitle", result.getName());

        // Проверка вызовов методов моков
        verify(jobTitleRepository, times(1)).findById(jobTitleId);
    }

    @DisplayName("deleteJobTitle method is works: ")
    @Test
    void deleteJobTitle() {
        // Задаем входные данные для теста
        long jobTitleId = 1L;

        // Создаем мок JobTitle, который должен вернуться из репозитория
        JobTitle existingJobTitle = new JobTitle(jobTitleId, "To Be Deleted",new ArrayList<Competa>());

        // Когда метод findById вызывается на моке репозитория, возвращаем существующий JobTitle
        when(jobTitleRepository.findById(jobTitleId)).thenReturn(Optional.of(existingJobTitle));

        // Вызываем метод, который мы тестируем
        JobTitleDto result = jobTitleService.deleteJobTitle(jobTitleId);

        // Проверяем, что метод deleteByIdJobTitle вызывается с ожидаемым аргументом
        verify(jobTitleRepository, times(1)).findById(jobTitleId);
        verify(jobTitleRepository, times(1)).deleteById(jobTitleId);

        // Проверяем, что результат метода соответствует ожиданиям
        assertEquals(Long.valueOf(existingJobTitle.getId()), result.getId());
        assertEquals(existingJobTitle.getName(), result.getName());
    }

    @DisplayName("getJobTitleOrElseThrow method is works: ")
    @Test
    void getJobTitleOrElseThrowException() {

        // Подготовка данных и моки
        long jobTitleId = 100L;

        // Настройка мок-объекта JobTitleRepository
        when(jobTitleRepository.findById(jobTitleId)).thenReturn(Optional.empty());

        // Проверка на выброс исключения
        assertThrows("Job title with id <" + jobTitleId + "> not found",
                RestApiException.class,
                () -> {
                    jobTitleService.getJobTitleOrElseThrow(jobTitleId);
                });
    }

}
