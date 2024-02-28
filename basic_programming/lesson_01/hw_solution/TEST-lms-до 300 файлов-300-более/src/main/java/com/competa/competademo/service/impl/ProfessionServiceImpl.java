package com.competa.competademo.service.impl;

import com.competa.competademo.dto.*;
import com.competa.competademo.entity.Profession;
import com.competa.competademo.exceptions.RestApiException;
import com.competa.competademo.repository.ProfessionRepository;
import com.competa.competademo.service.ProfessionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Service
public class ProfessionServiceImpl implements ProfessionService {

    ProfessionRepository professionRepository;

    @Override
    public ProfessionDto addProfession(NewProfessionDto newProfession) {
        Profession profession = new Profession();
        profession.setName(newProfession.getName());
        if (!isProfessionExistsByName(newProfession.getName())) {
            professionRepository.save(profession);
        }
        return ProfessionDto.from(profession);
    }

    @Override
    public ProfessionDto updateProfession(long id, NewProfessionDto updateProfession) {
        Profession profession = getProfessionOrElseThrow(id);
        profession.setName(updateProfession.getName());
        if (!isProfessionExistsByName(updateProfession.getName())) {
            professionRepository.save(profession);
        }
        return ProfessionDto.from(profession);
    }

    @Override
    public List<ProfessionDto> getAllProfession() {
        List<Profession> professions = professionRepository.findAll();
        return ProfessionDto.from(professions);
    }

    @Override
    public ProfessionDto getByIdProfession(long id) {
        Profession profession = getProfessionOrElseThrow(id);
        return ProfessionDto.from(profession);
    }

    @Override
    public ProfessionDto deleteProfession(long id) {
        Profession profession = getProfessionOrElseThrow(id);
        professionRepository.deleteById(id);
        return ProfessionDto.from(profession);
    }

    /**
     * @param id long - primary key table
     * @return if the record is not found returns an exception with the message
     */
    public Profession getProfessionOrElseThrow(long id) {
        return professionRepository.findById(id)
                .orElseThrow(() -> new RestApiException(HttpStatus.NOT_FOUND,
                        "Profession with id " + id + " not found"));
    }

    /**
     * @param name String - value field in table
     * @return if a record with this field value already exists, it returns an exception with a message,
     * if not, it returns false
     */
    public boolean isProfessionExistsByName(String name) {
        if (professionRepository.existsByNameIgnoreCase(name)) {
            throw new RestApiException(HttpStatus.CONFLICT,
                    "Profession with name " + name + " already exists");
        }
        return false;
    }
}
