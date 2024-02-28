package com.competa.competademo.service.impl;

import com.competa.competademo.dto.NewSoftSkillDto;
import com.competa.competademo.dto.SoftSkillDto;
import com.competa.competademo.entity.SoftSkill;
import com.competa.competademo.exceptions.RestApiException;
import com.competa.competademo.repository.SoftSkillRepository;
import com.competa.competademo.service.SoftSkillsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.competa.competademo.dto.SoftSkillDto.from;

/**
 * @author Oleg Karimov
 *
 */
@Service
@RequiredArgsConstructor
public class SoftSkillsServiceImpl implements SoftSkillsService {

    private final SoftSkillRepository softSkillRepository;

    @Override
    public List<SoftSkillDto> getAllSoftSkills() {
        List<SoftSkill> softSkills = softSkillRepository.findAll();
        return SoftSkillDto.from(softSkills);
    }

    @Override
    public SoftSkillDto getSoftSkill(long softSkillId) {
        return SoftSkillDto.from(getSoftSkillOrThrow(softSkillId));
    }

    SoftSkill getSoftSkillOrThrow(long softSkillId) {
        return softSkillRepository.findById(softSkillId)
                .orElseThrow(() ->
                        new RestApiException(HttpStatus.NOT_FOUND, "Soft Skill with id <" + softSkillId + "> not found"));
    }

    @Override
    public SoftSkillDto createSoftSkill(NewSoftSkillDto newSoftSkill) {
        checkSoftSkillName(newSoftSkill);
        SoftSkill softSkill = new SoftSkill();
        softSkill.setName(newSoftSkill.getName());
        softSkillRepository.save(softSkill);
        return SoftSkillDto.from(softSkill);
    }

    private void checkSoftSkillName(NewSoftSkillDto newSoftSkill) {
        if (softSkillRepository.existsByNameIgnoreCase(newSoftSkill.getName())) {
            throw new RestApiException(HttpStatus.CONFLICT,
                    "SoftSkill with name '" + newSoftSkill.getName() + "' already exists.");
        }
    }

    @Override
    public SoftSkillDto updateSoftSkill(long softSkillId, NewSoftSkillDto newSoftSkill) {
        SoftSkill softSkill = getSoftSkillOrThrow(softSkillId);
        checkSoftSkillName(newSoftSkill);
        if (!Objects.equals(softSkill.getName(), newSoftSkill.getName())){
            softSkill.setName(newSoftSkill.getName());
            softSkillRepository.save(softSkill);
        }
        return SoftSkillDto.from(softSkill);
    }

    @Override
    public SoftSkillDto deleteSoftSkill(long softSkillId) {
        SoftSkill softSkill = getSoftSkillOrThrow(softSkillId);
        softSkillRepository.deleteById(softSkillId);
        return from(softSkill);
    }
}
