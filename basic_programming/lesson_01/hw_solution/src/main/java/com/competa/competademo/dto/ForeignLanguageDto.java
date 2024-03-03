package com.competa.competademo.dto;

import com.competa.competademo.entity.ForeignLanguage;
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
public class ForeignLanguageDto {

    private long id;

    private String name;

    public static ForeignLanguageDto from (ForeignLanguage foreignLanguage) {
        return ForeignLanguageDto.builder()
                .id(foreignLanguage.getId())
                .name(foreignLanguage.getName())
                .build();
    }

    public static List<ForeignLanguageDto> from(Collection<ForeignLanguage> foreignLanguages){
        return foreignLanguages.stream()
                .map(ForeignLanguageDto::from)
                .collect(Collectors.toList());
    }
}
