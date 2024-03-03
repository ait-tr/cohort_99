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
@Schema(description = "Adding a HardSkill")
public class NewHardSkillDto {
    @NotBlank
    @Schema(description = "HardSkill name", example = "Java Spring Boot")
    private String name;

}
