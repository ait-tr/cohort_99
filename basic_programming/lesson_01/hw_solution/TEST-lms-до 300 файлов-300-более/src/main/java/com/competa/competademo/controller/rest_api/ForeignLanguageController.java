package com.competa.competademo.controller.rest_api;

import com.competa.competademo.controller.rest_api.api.ForeignLanguageApi;
import com.competa.competademo.dto.ForeignLanguageDto;
import com.competa.competademo.entity.ForeignLanguage;
import com.competa.competademo.service.ForeignLanguageService;
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
public class ForeignLanguageController implements ForeignLanguageApi {

    ForeignLanguageService foreignLanguageService;

    @Override
    public ForeignLanguageDto addForeignLanguage(@RequestBody ForeignLanguage foreignLanguage) {
        return foreignLanguageService.createForeignLanguage(foreignLanguage);
    }

    @Override
    public List<ForeignLanguageDto> getAllForeignLanguage() {
        return foreignLanguageService.getAllForeignLanguage();
    }

    @Override
    public ForeignLanguageDto updateForeignLanguage(@PathVariable Long id, @RequestBody ForeignLanguage foreignLanguage) {
        return foreignLanguageService.updateForeignLanguage(id, foreignLanguage);
    }

    @Override
    public void deleteForeignLanguage(@PathVariable Long id) {
        foreignLanguageService.deleteForeignLanguage(id);
    }
}

