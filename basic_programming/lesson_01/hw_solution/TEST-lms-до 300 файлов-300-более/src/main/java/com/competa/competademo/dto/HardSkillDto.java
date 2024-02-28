package com.competa.competademo.dto;

import com.competa.competademo.entity.HardSkill;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Oleg Karimov
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Hard Skill")
public class HardSkillDto {
    @Schema(description = "Hard Skill id", example = "1")
    private long id;

    @Schema(description = "Hard Skill name", example = "team work")
    private String name;

    public static HardSkillDto from (HardSkill hardSkill) {
        return HardSkillDto.builder()
                .id(hardSkill.getId())
                .name(hardSkill.getName())
                .build();
    }

    public static List<HardSkillDto> from(Collection<HardSkill> hardSkills){
        return hardSkills.stream()
                .map(HardSkillDto::from)
                .collect(Collectors.toList());
    }

}
