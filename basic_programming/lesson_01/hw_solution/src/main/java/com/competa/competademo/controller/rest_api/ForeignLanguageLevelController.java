package com.competa.competademo.controller.rest_api;

import com.competa.competademo.controller.rest_api.api.ForeignLanguageLevelApi;
import com.competa.competademo.dto.ForeignLanguageLevelDto;
import com.competa.competademo.entity.ForeignLanguageLevel;
import com.competa.competademo.service.ForeignLanguageLevelService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@RestController
public class ForeignLanguageLevelController implements ForeignLanguageLevelApi {

    ForeignLanguageLevelService foreignLanguageLevelService;

    @Override
    public ForeignLanguageLevelDto addForeignLanguageLevel(@RequestBody ForeignLanguageLevel foreignLanguageLevel) {
        return foreignLanguageLevelService.createForeignLanguageLevel(foreignLanguageLevel);
    }

    @Override
    public List<ForeignLanguageLevelDto> getAllForeignLanguageLevel() {
        return foreignLanguageLevelService.getAllForeignLanguageLevel();
    }

    @Override
    public ForeignLanguageLevelDto updateForeignLanguageLevel(@PathVariable Long id, @RequestBody ForeignLanguageLevel foreignLanguageLevel) {
        return foreignLanguageLevelService.updateForeignLanguageLevel(id, foreignLanguageLevel);
    }

    @Override
    public void deleteForeignLanguageLevel(@PathVariable Long id) {
        foreignLanguageLevelService.deleteForeignLanguageLevel(id);
    }
}

