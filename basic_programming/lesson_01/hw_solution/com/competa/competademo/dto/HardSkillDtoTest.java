package com.competa.competademo.dto;

import com.competa.competademo.entity.Competa;
import com.competa.competademo.entity.HardSkill;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Oleg Karimov
 */
@DisplayName("HardSkillsServiceImpl is works: ")
public class HardSkillDtoTest {
    @Test
    public void testFromHardSkill() {
        // Создаём объект HardSkill для теста
        HardSkill hardSkill = new HardSkill();
        hardSkill.setName("Test HardSkill");

        // Вызываем метод from для создания HardSkillDto
        HardSkillDto hardSkillDto = HardSkillDto.from(hardSkill);

        // Проверка, что созданный объект HardSkillDto соответствует ожиданиям
        assertEquals(hardSkill.getId(), hardSkillDto.getId());
        assertEquals(hardSkill.getName(), hardSkillDto.getName());
    }

    @Test
    public void testListFromHardSkills() {
        // Создаём коллекцию объектов HardSkill для теста
        Competa competa = new Competa();
        HardSkill hardSkill1 = new HardSkill(1L, "HardSkill 1",competa);
        HardSkill hardSkill2 = new HardSkill(2L, "HardSkill 2",competa);
        List<HardSkill> hardSkillList = Arrays.asList(hardSkill1, hardSkill2);

        // Вызываем метод from для создания списка HardSkillDto
        List<HardSkillDto> hardSkillDtoList = HardSkillDto.from(hardSkillList);

        // Проверка, что созданный список HardSkillDto соответствует ожиданиям
        assertEquals(hardSkillList.size(), hardSkillDtoList.size());

        HardSkillDto dto1 = hardSkillDtoList.get(0);
        assertEquals(hardSkill1.getId(), dto1.getId());
        assertEquals(hardSkill1.getName(), dto1.getName());

        HardSkillDto dto2 = hardSkillDtoList.get(1);
        assertEquals(hardSkill2.getId(), dto2.getId());
        assertEquals(hardSkill2.getName(), dto2.getName());
    }
}
