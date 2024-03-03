package com.competa.competademo.service.impl;

import com.competa.competademo.dto.ForeignLanguageDto;
import com.competa.competademo.entity.ForeignLanguage;
import com.competa.competademo.exceptions.RestApiException;
import com.competa.competademo.repository.ForeignLanguageRepository;
import com.competa.competademo.service.ForeignLanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.competa.competademo.dto.ForeignLanguageDto.from;


@RequiredArgsConstructor
@Service
public class ForeignLanguageServiceImpl implements ForeignLanguageService {

    private final ForeignLanguageRepository foreignLanguageRepository;

    @Override
    public ForeignLanguageDto createForeignLanguage(ForeignLanguage foreignLanguage) {
        foreignLanguageRepository.save(foreignLanguage);
        return ForeignLanguageDto.from(foreignLanguage);
    }

    ForeignLanguage getForeignLanguageOrThrow(Long id) {
        return foreignLanguageRepository.findById(id)
                .orElseThrow(() ->
                        new RestApiException(HttpStatus.NOT_FOUND, "Foreign Language with id <" + id + "> not found"));
    }

    @Override
    public List<ForeignLanguageDto> getAllForeignLanguage() {
        List<ForeignLanguage> foreignLanguages = foreignLanguageRepository.findAll();
        return ForeignLanguageDto.from(foreignLanguages);
    }

    @Override
    public ForeignLanguageDto updateForeignLanguage(Long id, ForeignLanguage foreignLanguage) {
        if (foreignLanguageRepository.existsById(id)) {
            foreignLanguage.setId(id);
            return ResponseEntity.ok().body(new ForeignLanguageDto()).getBody();
        } else {
            return ForeignLanguageDto.from(foreignLanguage);
        }
    }

    @Override
    public ForeignLanguageDto deleteForeignLanguage(Long id) {
        ForeignLanguage foreignLanguage = getForeignLanguageOrThrow(id);
        foreignLanguageRepository.deleteById(id);

        return from(foreignLanguage);
    }
}
