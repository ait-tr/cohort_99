package com.competa.competademo.service;

import com.competa.competademo.dto.NewSoftSkillDto;
import com.competa.competademo.dto.SoftSkillDto;
import java.util.List;

/**
 * @author Oleg Karimov
 *
 * Этот интерфейс реализует методы CRUD для SoftSkill
 */
public interface SoftSkillsService {
    /**
     * Метод возвращает все SoftSkills
     */
    List<SoftSkillDto> getAllSoftSkills();

    /**
     * Метод возвращает один SoftSkill по заданному softSkillId
     * <p>
     * При отсутствии SoftSkill с заданным softSkillId, метод бросает исключение RuntimeException,
     *  которое перехватывается и на выходе статус ошибки 404: NOT_FOUND, с сообщением:
     *  "Soft Skill with id <" + softSkillId + "> not found"
     */
    SoftSkillDto getSoftSkill(long softSkillId);

    /**
     * Метод создаёт новый SoftSkill
     * <p>
     * При отсутствии SoftSkill с заданным softSkillId, метод бросает исключение RuntimeException,
     *  которое перехватывается и на выходе статус ошибки 404: NOT_FOUND, с сообщением:
     *  "Soft Skill with id <" + softSkillId + "> not found"
     * <p>
     * При дублировании имён SoftSkill, метод бросает исключение RuntimeException,
     *  которое перехватывается и на выходе статус ошибки 409: CONFLICT, с сообщением:
     *  "SoftSkill with name '" + newSoftSkill.getName() + "' already exists."
     */
    SoftSkillDto createSoftSkill(NewSoftSkillDto newSoftSkill);

    /**
     * Метод обновляет SoftSkill по заданному Id
     *
     * @param softSkillId Id SoftSkill которого нужно обновить
     * @param newSoftSkill новый SoftSkill
     * <p>
     * При отсутствии SoftSkill с заданным softSkillId, метод бросает исключение RuntimeException,
     *  которое перехватывается и на выходе статус ошибки 404: NOT_FOUND, с сообщением:
     *  "Soft Skill with id <" + softSkillId + "> not found"
     * <p>
     * При дублировании имён SoftSkill, метод бросает исключение RuntimeException,
     *  которое перехватывается и на выходе статус ошибки 409: CONFLICT, с сообщением:
     *  "SoftSkill with name '" + newSoftSkill.getName() + "' already exists."
     */
    SoftSkillDto updateSoftSkill(long softSkillId, NewSoftSkillDto newSoftSkill);

    /**
     * Метод удаляет один SoftSkill по заданному softSkillId
     * <p>
     * При отсутствии SoftSkill с заданным softSkillId, метод бросает исключение RuntimeException,
     *  которое перехватывается и на выходе статус ошибки 404: NOT_FOUND, с сообщением:
     *  "Soft Skill with id <" + softSkillId + "> not found"
     */
    SoftSkillDto deleteSoftSkill(long softSkillId);
}
