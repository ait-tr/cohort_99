package com.competa.competademo.dto;

import com.competa.competademo.entity.EduLevel;
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
@Schema(description = "Education Level")
public class EduLevelDto {

    @Schema(description = "Education level ID", example = "1")
    private Long id;

    @Schema(description = "Title of education level", example = "начальное")
    private String name;

    public static EduLevelDto from(EduLevel eduLevel) {
        return EduLevelDto.builder()
                .id(eduLevel.getId())
                .name(eduLevel.getName())
                .build();
    }

    public static List<EduLevelDto> from(List<EduLevel> eduLevels) {
        return eduLevels.stream()
                .map(EduLevelDto::from)
                .collect(Collectors.toList());
    }

}
