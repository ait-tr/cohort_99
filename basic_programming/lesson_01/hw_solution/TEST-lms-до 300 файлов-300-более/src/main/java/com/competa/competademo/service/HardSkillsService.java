package com.competa.competademo.service;

import com.competa.competademo.dto.HardSkillDto;
import com.competa.competademo.dto.NewHardSkillDto;

import java.util.List;

/**
 * @author Oleg Karimov
 *
 * Этот интерфейс реализует методы CRUD для HardSkill
 */
public interface HardSkillsService {
    /**
     * Метод возвращает все HardSkills
     */
    List<HardSkillDto> getAllHardSkills();

    /**
     * Метод возвращает один HardSkill по заданному hardSkillId
     * <p>
     * При отсутствии HardSkill с заданным hardSkillId, метод бросает исключение RuntimeException,
     *  которое перехватывается и на выходе статус ошибки 404: NOT_FOUND, с сообщением:
     *  "Hard Skill with id <" + hardSkillId + "> not found"
     */
    HardSkillDto getHardSkillById(long hardSkillId);

    /**
     * Метод создаёт новый HardSkill
     * <p>
     * При отсутствии HardSkill с заданным hardSkillId, метод бросает исключение RuntimeException,
     *  которое перехватывается и на выходе статус ошибки 404: NOT_FOUND, с сообщением:
     *  "Hard Skill with id <" + hardSkillId + "> not found"
     * <p>
     * При дублировании имён HardSkill, метод бросает исключение RuntimeException,
     *  которое перехватывается и на выходе статус ошибки 409: CONFLICT, с сообщением:
     *  "HardSkill with name '" + newHardSkill.getName() + "' already exists."
     */
    HardSkillDto createHardSkill(NewHardSkillDto newHardSkill);

    /**
     * Метод обновляет HardSkill по заданному Id
     *
     * @param hardSkillId Id HardSkill которого нужно обновить
     * @param newHardSkill новый HardSkill
     * <p>
     * При отсутствии HardSkill с заданным hardSkillId, метод бросает исключение RuntimeException,
     *  которое перехватывается и на выходе статус ошибки 404: NOT_FOUND, с сообщением:
     *  "Hard Skill with id <" + hardSkillId + "> not found"
     * <p>
     * При дублировании имён HardSkill, метод бросает исключение RuntimeException,
     *  которое перехватывается и на выходе статус ошибки 409: CONFLICT, с сообщением:
     *  "HardSkill with name '" + newHardSkill.getName() + "' already exists."
     */
    HardSkillDto updateHardSkill(long hardSkillId, NewHardSkillDto newHardSkill);

    /**
     * Метод удаляет один HardSkill по заданному hardSkillId
     * <p>
     * При отсутствии HardSkill с заданным hardSkillId, метод бросает исключение RuntimeException,
     *  которое перехватывается и на выходе статус ошибки 404: NOT_FOUND, с сообщением:
     *  "Hard Skill with id <" + hardSkillId + "> not found"
     */
    HardSkillDto deleteHardSkill(long hardSkillId);
}
