package com.competa.competademo.service;

import com.competa.competademo.dto.ForeignLanguageLevelDto;
import com.competa.competademo.entity.ForeignLanguageLevel;

import java.util.List;

public interface ForeignLanguageLevelService {

    List<ForeignLanguageLevelDto> getAllForeignLanguageLevel();
    ForeignLanguageLevelDto createForeignLanguageLevel(ForeignLanguageLevel foreignLanguageLevel);
    ForeignLanguageLevelDto updateForeignLanguageLevel(Long id, ForeignLanguageLevel foreignLanguageLevel);
    ForeignLanguageLevelDto deleteForeignLanguageLevel(Long id);
}
