package com.competa.competademo.service;

import com.competa.competademo.dto.ForeignLanguageDto;
import com.competa.competademo.entity.ForeignLanguage;

import java.util.List;

public interface ForeignLanguageService {
    List<ForeignLanguageDto> getAllForeignLanguage();
    ForeignLanguageDto createForeignLanguage(ForeignLanguage foreignLanguage);
    ForeignLanguageDto updateForeignLanguage(Long id, ForeignLanguage foreignLanguage);
    ForeignLanguageDto deleteForeignLanguage(Long id);
}
