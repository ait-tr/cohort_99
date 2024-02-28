package com.competa.competademo.controller.rest_api;

import com.competa.competademo.controller.rest_api.api.DriverLicenceApi;
import com.competa.competademo.dto.DriverLicenceDto;
import com.competa.competademo.dto.NewDriverLicenceDto;
import com.competa.competademo.service.DriverLicenceService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@RestController
public class DriverLicenceController implements DriverLicenceApi {

    DriverLicenceService driverLicenceService;

    @Override
    public DriverLicenceDto addDriverLicence(NewDriverLicenceDto newDriverLicence) {
        return driverLicenceService.addDriverLicence(newDriverLicence);
    }

    @Override
    public DriverLicenceDto updateDriverLicence(long id, NewDriverLicenceDto updateDriverLicence) {
        return driverLicenceService.updateDriverLicence(id, updateDriverLicence);
    }

    @Override
    public List<DriverLicenceDto> getAllDriverLicence() {
        return driverLicenceService.getAllDriverLicence();
    }

    @Override
    public DriverLicenceDto deleteDriverLicence(long id) {
        return driverLicenceService.deleteDriverLicence(id);
    }
}
