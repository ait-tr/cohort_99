package com.competa.competademo.dto;

import com.competa.competademo.entity.ForeignLanguageLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForeignLanguageLevelDto {

    private long id;

    private String name;

    public static ForeignLanguageLevelDto from (ForeignLanguageLevel foreignLanguageLevel) {
        return ForeignLanguageLevelDto.builder()
                .id(foreignLanguageLevel.getId())
                .name(foreignLanguageLevel.getName())
                .build();
    }

    public static List<ForeignLanguageLevelDto> from(Collection<ForeignLanguageLevel> foreignLanguagesLevel){
        return foreignLanguagesLevel.stream()
                .map(ForeignLanguageLevelDto::from)
                .collect(Collectors.toList());
    }
}
