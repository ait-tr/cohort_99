package com.competa.competademo.dto;

import com.competa.competademo.entity.SoftSkill;
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
@Schema(description = "Soft Skill")
public class SoftSkillDto {

    @Schema(description = "Soft Skill id", example = "1")
    private long id;

    @Schema(description = "Soft Skill name", example = "team work")
    private String name;

    public static SoftSkillDto from (SoftSkill softSkill) {
        return SoftSkillDto.builder()
                .id(softSkill.getId())
                .name(softSkill.getName())
                .build();
    }

    public static List<SoftSkillDto> from(Collection<SoftSkill> softSkills){
        return softSkills.stream()
                .map(SoftSkillDto::from)
                .collect(Collectors.toList());
    }

}
