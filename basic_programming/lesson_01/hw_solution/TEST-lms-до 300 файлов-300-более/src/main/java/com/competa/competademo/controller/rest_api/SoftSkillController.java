package com.competa.competademo.controller.rest_api;

import com.competa.competademo.controller.rest_api.api.SoftSkillApi;
import com.competa.competademo.dto.NewSoftSkillDto;
import com.competa.competademo.dto.SoftSkillDto;
import com.competa.competademo.service.SoftSkillsService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Oleg Karimov
 */
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@RestController
public class SoftSkillController implements SoftSkillApi {

    SoftSkillsService softSkillsService;

    @Override
    public List<SoftSkillDto> getAllSoftSkills() {
        return softSkillsService.getAllSoftSkills();
    }

    @Override
    public SoftSkillDto createSoftSkill(NewSoftSkillDto newSoftSkill) {
        return softSkillsService.createSoftSkill(newSoftSkill);
    }

    @Override
    public SoftSkillDto updateSoftSkill(long softSkillId, NewSoftSkillDto newSoftSkill) {
        return softSkillsService.updateSoftSkill(softSkillId, newSoftSkill);
    }

    @Override
    public SoftSkillDto deleteSoftSkill(long softSkillId) {
        return softSkillsService.deleteSoftSkill(softSkillId);
    }
}
