package com.competa.competademo.dto;

import com.competa.competademo.entity.SoftSkill;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Oleg Karimov
 */
@DisplayName("SoftSkillsServiceImpl is works: ")
class SoftSkillDtoTest {

    @Test
    public void testFromSoftSkill() {
        // Создаём объект SoftSkill для теста
        SoftSkill softSkill = new SoftSkill();
        softSkill.setName("Test SoftSkill");

        // Вызываем метод from для создания SoftSkillDto
        SoftSkillDto softSkillDto = SoftSkillDto.from(softSkill);

        // Проверка, что созданный объект SoftSkillDto соответствует ожиданиям
        assertEquals(softSkill.getId(), softSkillDto.getId());
        assertEquals(softSkill.getName(), softSkillDto.getName());
    }

    @Test
    public void testListFromSoftSkills() {
        // Создаём коллекцию объектов SoftSkill для теста
        SoftSkill softSkill1 = new SoftSkill(1L, "SoftSkill 1");
        SoftSkill softSkill2 = new SoftSkill(2L, "SoftSkill 2");
        List<SoftSkill> softSkillList = Arrays.asList(softSkill1, softSkill2);

        // Вызываем метод from для создания списка SoftSkillDto
        List<SoftSkillDto> softSkillDtoList = SoftSkillDto.from(softSkillList);

        // Проверка, что созданный список SoftSkillDto соответствует ожиданиям
        assertEquals(softSkillList.size(), softSkillDtoList.size());

        SoftSkillDto dto1 = softSkillDtoList.get(0);
        assertEquals(softSkill1.getId(), dto1.getId());
        assertEquals(softSkill1.getName(), dto1.getName());

        SoftSkillDto dto2 = softSkillDtoList.get(1);
        assertEquals(softSkill2.getId(), dto2.getId());
        assertEquals(softSkill2.getName(), dto2.getName());
    }
}