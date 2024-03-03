package com.competa.competademo.service;

import com.competa.competademo.dto.DriverLicenceDto;
import com.competa.competademo.dto.NewDriverLicenceDto;

import java.util.List;

public interface DriverLicenceService {
    DriverLicenceDto addDriverLicence(NewDriverLicenceDto newDriverLicence);

    DriverLicenceDto updateDriverLicence(long id, NewDriverLicenceDto updateDriverLicence);

    List<DriverLicenceDto> getAllDriverLicence();

    DriverLicenceDto getByIdDriverLicence(long id);

    DriverLicenceDto deleteDriverLicence(long id);
}
