package com.competa.competademo.dto;

import com.competa.competademo.entity.Competa;
import com.competa.competademo.entity.JobTitle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@ExtendWith(MockitoExtension.class)
@DisplayName("JobTitleServiceImpl is works: ")
class JobTitleDtoTest {

    @Test
    public void testFromJobTitle() {
        // Создаём объект jobTitle для теста
        JobTitle jobTitle = new JobTitle();
        jobTitle.setName("Test JobTitle");

        // Вызываем метод from для создания JobTitleDto
        JobTitleDto jobTitleDto = JobTitleDto.from(jobTitle);

        // Проверка, что созданный объект JobTitleDto соответствует ожиданиям
        assertEquals(Long.valueOf(jobTitle.getId()), jobTitleDto.getId());
        assertEquals(jobTitle.getName(), jobTitleDto.getName());
    }

    @Test
    public void testListFromJobTitles() {
        // Создаём коллекцию объектов JobTitle для теста
        JobTitle jobTitle1 = new JobTitle(1L, "JobTitle 1",new ArrayList<Competa>() );
        JobTitle jobTitle2 = new JobTitle(2L, "JobTitle 2",new ArrayList<Competa>());
        List<JobTitle> jobTitleList = Arrays.asList(jobTitle1, jobTitle2);

        // Вызываем метод from для создания списка JobTitleDto
        List<JobTitleDto> jobTitleDtoList = JobTitleDto.from(jobTitleList);

        // Проверка, что созданный список JobTitleDto соответствует ожиданиям
        assertEquals(jobTitleList.size(), jobTitleDtoList.size());

        JobTitleDto dto1 = jobTitleDtoList.get(0);
        assertEquals(Long.valueOf(jobTitle1.getId()), dto1.getId());
        assertEquals(jobTitle1.getName(), dto1.getName());

        JobTitleDto dto2 = jobTitleDtoList.get(1);
        assertEquals(Long.valueOf(jobTitle2.getId()), dto2.getId());
        assertEquals(jobTitle2.getName(), dto2.getName());
    }
}
