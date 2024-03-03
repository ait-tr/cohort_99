package com.competa.competademo.service;

import com.competa.competademo.dto.NewProfessionDto;
import com.competa.competademo.dto.ProfessionDto;

import java.util.List;

public interface ProfessionService {
    ProfessionDto addProfession(NewProfessionDto newProfession);

    ProfessionDto updateProfession(long id, NewProfessionDto updateProfession);

    List<ProfessionDto> getAllProfession();

    ProfessionDto getByIdProfession(long id);

    ProfessionDto deleteProfession(long id);
}
