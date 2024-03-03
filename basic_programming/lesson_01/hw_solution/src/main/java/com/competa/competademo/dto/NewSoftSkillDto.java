package com.competa.competademo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Oleg Karimov
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Adding a SoftSkill")
public class NewSoftSkillDto {
    @NotBlank
    @Schema(description = "SoftSkill name", example = "team work")
    private String name;

}
