package com.competa.competademo.dto;

import com.competa.competademo.entity.Profession;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Profession")
public class ProfessionDto {

    @Schema(description = "Profession ID", example = "1")
    private Long id;

    @Schema(description = "Title of profession", example = "инженер-конструктор")
    private String name;

    public static ProfessionDto from(Profession profession) {
        return ProfessionDto.builder()
                .id(profession.getId())
                .name(profession.getName())
                .build();
    }

    public static List<ProfessionDto> from(List<Profession> professions) {
        return professions.stream()
                .map(ProfessionDto::from)
                .collect(Collectors.toList());
    }

}
