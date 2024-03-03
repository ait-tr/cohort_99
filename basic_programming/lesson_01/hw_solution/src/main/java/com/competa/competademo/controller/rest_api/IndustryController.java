package com.competa.competademo.controller.rest_api;

import com.competa.competademo.controller.rest_api.api.IndustryApi;
import com.competa.competademo.dto.IndustryDto;
import com.competa.competademo.dto.NewIndustryDto;
import com.competa.competademo.service.IndustryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@RestController
public class IndustryController implements IndustryApi {

    IndustryService industryService;

    @Override
    public IndustryDto addIndustry(NewIndustryDto newIndustry) {
        return industryService.addIndustry(newIndustry);
    }

    @Override
    public IndustryDto updateIndustry(long id, NewIndustryDto updateIndustry) {
        return industryService.updateIndustry(id, updateIndustry);
    }

    @Override
    public List<IndustryDto> getAllIndustry() {
        return industryService.getAllIndustry();
    }

    @Override
    public IndustryDto deleteIndustry(long id) {
        return industryService.deleteIndustry(id);
    }

}
