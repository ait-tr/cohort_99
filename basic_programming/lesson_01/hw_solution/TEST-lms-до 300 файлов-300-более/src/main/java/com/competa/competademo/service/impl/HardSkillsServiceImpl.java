package com.competa.competademo.service.impl;

import com.competa.competademo.dto.HardSkillDto;
import com.competa.competademo.dto.NewHardSkillDto;
import com.competa.competademo.entity.HardSkill;
import com.competa.competademo.exceptions.RestApiException;
import com.competa.competademo.repository.HardSkillRepository;
import com.competa.competademo.service.HardSkillsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.competa.competademo.dto.HardSkillDto.from;

/**
 * @author Oleg Karimov
 *
 */
@Service
@RequiredArgsConstructor
public class HardSkillsServiceImpl implements HardSkillsService {

    private final HardSkillRepository hardSkillRepository;

    @Override
    public List<HardSkillDto> getAllHardSkills() {
        List<HardSkill> hardSkills = hardSkillRepository.findAll();
        return HardSkillDto.from(hardSkills);
    }

    @Override
    public HardSkillDto getHardSkillById(long hardSkillId) {
        return HardSkillDto.from(getHardSkillOrThrow(hardSkillId));
    }

    HardSkill getHardSkillOrThrow(long hardSkillId) {
        return hardSkillRepository.findById(hardSkillId)
                .orElseThrow(() ->
                        new RestApiException(HttpStatus.NOT_FOUND, "Hard Skill with id <" + hardSkillId + "> not found"));
    }

    @Override
    public HardSkillDto createHardSkill(NewHardSkillDto newHardSkill) {
        notExistHardSkillOrTrow(newHardSkill);
        HardSkill hardSkill = new HardSkill();
        hardSkill.setName(newHardSkill.getName());
        hardSkillRepository.save(hardSkill);
        return HardSkillDto.from(hardSkill);
    }

    private void notExistHardSkillOrTrow(NewHardSkillDto newHardSkill) {
        if (hardSkillRepository.existsByNameIgnoreCase(newHardSkill.getName())) {
            throw new RestApiException(HttpStatus.CONFLICT,
                    "HardSkill with name '" + newHardSkill.getName() + "' already exists.");
        }
    }

    @Override
    public HardSkillDto updateHardSkill(long hardSkillId, NewHardSkillDto newHardSkill) {
        HardSkill hardSkill = getHardSkillOrThrow(hardSkillId);
        notExistHardSkillOrTrow(newHardSkill);
        if (!Objects.equals(hardSkill.getName(), newHardSkill.getName())){
            hardSkill.setName(newHardSkill.getName());
            hardSkillRepository.save(hardSkill);
        }
        return HardSkillDto.from(hardSkill);
    }

    @Override
    public HardSkillDto deleteHardSkill(long hardSkillId) {
        HardSkill hardSkill = getHardSkillOrThrow(hardSkillId);

        if (hardSkill.getCompeta() != null){
            throw new RestApiException(HttpStatus.BAD_REQUEST,"Cannot be deleted because competa with id:"+ hardSkill.getCompeta().getId() +
                    "use this hard skill");
        }

        hardSkillRepository.deleteById(hardSkillId);

        return from(hardSkill);
    }
}
