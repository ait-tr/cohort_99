package com.competa.competademo.controller.rest_api;

import com.competa.competademo.controller.rest_api.api.HardSkillApi;
import com.competa.competademo.dto.HardSkillDto;
import com.competa.competademo.dto.NewHardSkillDto;
import com.competa.competademo.service.HardSkillsService;
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
public class HardSkillController implements HardSkillApi {

    HardSkillsService hardSkillsService;

    @Override
    public List<HardSkillDto> getAllHardSkills() {
        return hardSkillsService.getAllHardSkills();
    }

    @Override
    public HardSkillDto createHardSkill(NewHardSkillDto newHardSkill) {
        return hardSkillsService.createHardSkill(newHardSkill);
    }

    @Override
    public HardSkillDto updateHardSkill(long hardSkillId, NewHardSkillDto newHardSkill) {
        return hardSkillsService.updateHardSkill(hardSkillId, newHardSkill);
    }

    @Override
    public HardSkillDto deleteHardSkill(long hardSkillId) {
        return hardSkillsService.deleteHardSkill(hardSkillId);
    }
}
