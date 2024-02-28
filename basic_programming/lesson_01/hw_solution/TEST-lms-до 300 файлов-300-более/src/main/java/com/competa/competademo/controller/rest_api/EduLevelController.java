package com.competa.competademo.controller.rest_api;

import com.competa.competademo.controller.rest_api.api.EduLevelApi;
import com.competa.competademo.dto.EduLevelDto;
import com.competa.competademo.dto.NewEduLevelDto;
import com.competa.competademo.service.EduLevelService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@RestController
public class EduLevelController implements EduLevelApi {

    EduLevelService eduLevelService;

    @Override
    public EduLevelDto addEduLevel(NewEduLevelDto newEduLevel) {
        return eduLevelService.addEduLevel(newEduLevel);
    }

    @Override
    public EduLevelDto updateEduLevel(long id, NewEduLevelDto updateEduLevel) {
        return eduLevelService.updateEduLevel(id, updateEduLevel);
    }

    @Override
    public List<EduLevelDto> getAllEduLevel() {
        return eduLevelService.getAllEduLevel();
    }

    @Override
    public EduLevelDto deleteEduLevel(long id) {
        return eduLevelService.deleteEduLevel(id);
    }

}
