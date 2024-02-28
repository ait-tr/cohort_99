package com.competa.competademo.service.impl;

import com.competa.competademo.dto.IndustryDto;
import com.competa.competademo.dto.NewIndustryDto;
import com.competa.competademo.entity.Industry;
import com.competa.competademo.exceptions.RestApiException;
import com.competa.competademo.repository.IndustryRepository;
import com.competa.competademo.service.IndustryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Service
public class IndustryServiceImpl implements IndustryService {

    // поле
    private final IndustryRepository industryRepository;

    @Override
    public IndustryDto addIndustry(NewIndustryDto newIndustry) {
        Industry industry = new Industry();
        industry.setName(newIndustry.getName());
        if (!isIndustryExistsByName(newIndustry.getName())) {
            industryRepository.save(industry);
        }
        return IndustryDto.from(industry);
    }

    @Override
    public IndustryDto updateIndustry(long id, NewIndustryDto updateIndustry) {
        Industry Industry = getIndustryOrElseThrow(id);
        Industry.setName(updateIndustry.getName());
        if (!isIndustryExistsByName(updateIndustry.getName())) {
            industryRepository.save(Industry);
        }
        return IndustryDto.from(Industry);
    }

    @Override
    public List<IndustryDto> getAllIndustry() {
        List<Industry> Industries = industryRepository.findAll();
        return IndustryDto.from(Industries);
    }

    @Override
    public IndustryDto getByIdIndustry(long id) {
        Industry Industry = getIndustryOrElseThrow(id);
        return IndustryDto.from(Industry);
    }

    @Override
    public IndustryDto deleteIndustry(long id) {
        Industry industry = getIndustryOrElseThrow(id);

        if (industry.getCompeta() != null){
            throw new RestApiException(HttpStatus.BAD_REQUEST,"Cannot be deleted because competa with id:"+ industry.getCompeta().getId() +
                    "use this industry");
        }

        industryRepository.deleteById(id);
        return IndustryDto.from(industry);
    }

//    @Override
//    public IndustryDto findById(long id) {
//        Industry Industry = getIndustryOrElseThrow(id);
//        return IndustryDto.from(Industry);
//    }

    /**
     * @param id long - primary key table
     * @return if the record is not found returns an exception with the message
     */
    public Industry getIndustryOrElseThrow(long id) {
        return industryRepository.findById(id)
                .orElseThrow(() -> new RestApiException(HttpStatus.NOT_FOUND,
                        "Education level with id " + id + " not found"));
    }

    /**
     * @param name String - value field in table
     * @return if a record with this field value already exists, it returns true and an exception with a message,
     * if not, it returns false
     */
    public boolean isIndustryExistsByName(String name) {
        if (industryRepository.existsByNameIgnoreCase(name)) {
            throw new RestApiException(HttpStatus.CONFLICT,
                    "Education level with name " + name + " already exists");
        }
        return false;
    }
    
}
