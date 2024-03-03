package com.competa.competademo.service;

import com.competa.competademo.dto.IndustryDto;
import com.competa.competademo.dto.NewIndustryDto;

import java.util.List;

public interface IndustryService {

    IndustryDto addIndustry(NewIndustryDto newIndustry);
    IndustryDto updateIndustry(long id, NewIndustryDto updateIndustry);
    List<IndustryDto> getAllIndustry();
    IndustryDto getByIdIndustry(long id);
    IndustryDto deleteIndustry(long id);

//    IndustryDto findById(long id);
    
}
