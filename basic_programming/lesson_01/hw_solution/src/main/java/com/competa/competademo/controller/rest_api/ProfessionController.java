package com.competa.competademo.controller.rest_api;

import com.competa.competademo.controller.rest_api.api.ProfessionApi;
import com.competa.competademo.dto.NewProfessionDto;
import com.competa.competademo.dto.ProfessionDto;
import com.competa.competademo.service.ProfessionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@RestController
public class ProfessionController implements ProfessionApi {

    ProfessionService professionService;

    @Override
    public ProfessionDto addProfession(NewProfessionDto newProfession) {
        return professionService.addProfession(newProfession);
    }

    @Override
    public ProfessionDto updateProfession(long id, NewProfessionDto updateProfession) {
        return professionService.updateProfession(id, updateProfession);
    }

    @Override
    public List<ProfessionDto> getAllProfession() {
        return professionService.getAllProfession();
    }

    @Override
    public ProfessionDto deleteProfession(long id) {
        return professionService.deleteProfession(id);
    }
}
