package com.competa.competademo.service.impl;

import com.competa.competademo.dto.EduLevelDto;
import com.competa.competademo.dto.NewEduLevelDto;
import com.competa.competademo.entity.EduLevel;
import com.competa.competademo.exceptions.RestApiException;
import com.competa.competademo.repository.EduLevelRepository;
import com.competa.competademo.service.EduLevelService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Service
public class EduLevelServiceImpl implements EduLevelService {

    EduLevelRepository eduLevelRepository;

    @Override
    public EduLevelDto addEduLevel(NewEduLevelDto newEduLevel) {
        EduLevel eduLevel = new EduLevel();
        eduLevel.setName(newEduLevel.getName());
        if (!isEduLevelExistsByName(newEduLevel.getName())) {
            eduLevelRepository.save(eduLevel);
        }
        return EduLevelDto.from(eduLevel);
    }

    @Override
    public EduLevelDto updateEduLevel(long id, NewEduLevelDto updateEduLevel) {
        EduLevel eduLevel = getEduLevelOrElseThrow(id);
        eduLevel.setName(updateEduLevel.getName());
        if (!isEduLevelExistsByName(updateEduLevel.getName())) {
            eduLevelRepository.save(eduLevel);
        }
        return EduLevelDto.from(eduLevel);
    }

    @Override
    public List<EduLevelDto> getAllEduLevel() {
        List<EduLevel> eduLevels = eduLevelRepository.findAll();
        return EduLevelDto.from(eduLevels);
    }

    @Override
    public EduLevelDto getByIdEduLevel(long id) {
        EduLevel eduLevel = getEduLevelOrElseThrow(id);
        return EduLevelDto.from(eduLevel);
    }

    @Override
    public EduLevelDto deleteEduLevel(long id) {
        EduLevel eduLevel = getEduLevelOrElseThrow(id);
        if (eduLevel.getCompeta() != null){
            throw new RestApiException(HttpStatus.BAD_REQUEST,"Cannot be deleted because competa with id:"+ eduLevel.getCompeta().getId() +
                    "use this EduLevel");
        }
        eduLevelRepository.deleteById(id);
        return EduLevelDto.from(eduLevel);
    }

    /**
     * @param id long - primary key table
     * @return if the record is not found returns an exception with the message
     */
    public EduLevel getEduLevelOrElseThrow(long id) {
        return eduLevelRepository.findById(id)
                .orElseThrow(() -> new RestApiException(HttpStatus.NOT_FOUND,
                        "Education level with id " + id + " not found"));
    }

    /**
     * @param name String - value field in table
     * @return if a record with this field value already exists, it returns true and an exception with a message,
     * if not, it returns false
     */
    public boolean isEduLevelExistsByName(String name) {
        if (eduLevelRepository.existsByNameIgnoreCase(name)) {
            throw new RestApiException(HttpStatus.CONFLICT,
                    "Education level with name " + name + " already exists");
        }
        return false;
    }

}
