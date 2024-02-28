package com.competa.competademo.service.impl;

import com.competa.competademo.dto.ForeignLanguageLevelDto;
import com.competa.competademo.entity.ForeignLanguageLevel;
import com.competa.competademo.exceptions.RestApiException;
import com.competa.competademo.repository.ForeignLanguageLevelRepository;
import com.competa.competademo.service.ForeignLanguageLevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.competa.competademo.dto.ForeignLanguageLevelDto.from;

@RequiredArgsConstructor
@Service
public class ForeignLanguageLevelServiceImpl implements ForeignLanguageLevelService {

    private final ForeignLanguageLevelRepository foreignLanguageLevelRepository;
    @Override
    public ForeignLanguageLevelDto createForeignLanguageLevel(ForeignLanguageLevel foreignLanguageLevel) {
        foreignLanguageLevelRepository.save(foreignLanguageLevel);
        return ForeignLanguageLevelDto.from(foreignLanguageLevel);
    }

    ForeignLanguageLevel getForeignLanguageLevelOrThrow(Long id) {
        return foreignLanguageLevelRepository.findById(id)
                .orElseThrow(() ->
                        new RestApiException(HttpStatus.NOT_FOUND, "Foreign Language Level with id <" + id + "> not found"));
    }

    @Override
    public List<ForeignLanguageLevelDto> getAllForeignLanguageLevel() {
        List<ForeignLanguageLevel> foreignLanguageLevels = foreignLanguageLevelRepository.findAll();
        return ForeignLanguageLevelDto.from(foreignLanguageLevels);
    }

    @Override
    public ForeignLanguageLevelDto updateForeignLanguageLevel(Long id, ForeignLanguageLevel foreignLanguageLevel) {
        if (foreignLanguageLevelRepository.existsById(id)) {
            foreignLanguageLevel.setId(id);
            return ResponseEntity.ok().body(new ForeignLanguageLevelDto()).getBody();
        } else {
            return ForeignLanguageLevelDto.from(foreignLanguageLevel);
        }
    }

    @Override
    public ForeignLanguageLevelDto deleteForeignLanguageLevel(Long id) {
        ForeignLanguageLevel foreignLanguageLevel = getForeignLanguageLevelOrThrow(id);
        foreignLanguageLevelRepository.deleteById(id);

        return from(foreignLanguageLevel);
    }
}
