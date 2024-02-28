package com.competa.competademo.service;

import com.competa.competademo.dto.EduLevelDto;
import com.competa.competademo.dto.NewEduLevelDto;

import java.util.List;

public interface EduLevelService {
    EduLevelDto addEduLevel(NewEduLevelDto newEduLevel);

    EduLevelDto updateEduLevel(long id, NewEduLevelDto updateEduLevel);

    List<EduLevelDto> getAllEduLevel();

    EduLevelDto getByIdEduLevel(long id);

    EduLevelDto deleteEduLevel(long id);
}
