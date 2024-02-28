package com.competa.competademo.service.impl;

import com.competa.competademo.dto.DriverLicenceDto;
import com.competa.competademo.dto.NewDriverLicenceDto;
import com.competa.competademo.entity.DriverLicence;
import com.competa.competademo.exceptions.RestApiException;
import com.competa.competademo.repository.DriverLicenceRepository;
import com.competa.competademo.service.DriverLicenceService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Service
public class DriverLicenceServiceImpl implements DriverLicenceService {

    DriverLicenceRepository driverLicenceRepository;

    @Override
    public DriverLicenceDto addDriverLicence(NewDriverLicenceDto newDriverLicence) {
        DriverLicence driverLicence = new DriverLicence();
        driverLicence.setName(newDriverLicence.getName());
        if (!isDriverLicenceExistsByName(newDriverLicence.getName())) {
            driverLicenceRepository.save(driverLicence);
        }
        return DriverLicenceDto.from(driverLicence);
    }

    @Override
    public DriverLicenceDto updateDriverLicence(long id, NewDriverLicenceDto updateDriverLicence) {
        DriverLicence driverLicence = getDriverLicenceOrElseThrow(id);
        driverLicence.setName(updateDriverLicence.getName());
        if (!isDriverLicenceExistsByName(updateDriverLicence.getName())) {
            driverLicenceRepository.save(driverLicence);
        }
        return DriverLicenceDto.from(driverLicence);
    }

    @Override
    public List<DriverLicenceDto> getAllDriverLicence() {
        List<DriverLicence> driverLicences = driverLicenceRepository.findAll();
        return DriverLicenceDto.from(driverLicences);
    }

    @Override
    public DriverLicenceDto getByIdDriverLicence(long id) {
        DriverLicence driverLicence = getDriverLicenceOrElseThrow(id);
        return DriverLicenceDto.from(driverLicence);
    }

    @Override
    public DriverLicenceDto deleteDriverLicence(long id) {
        DriverLicence driverLicence = getDriverLicenceOrElseThrow(id);
        if (driverLicence.getCompeta() != null){
            throw new RestApiException(HttpStatus.BAD_REQUEST,"Cannot be deleted because competa with id:"+ driverLicence.getCompeta().getId() +
                    "use this driver licence");
        }
        driverLicenceRepository.deleteById(id);
        return DriverLicenceDto.from(driverLicence);
    }

    /**
     * @param id long - primary key table
     * @return if the record is not found returns an exception with the message
     */
    public DriverLicence getDriverLicenceOrElseThrow(long id) {
        return driverLicenceRepository.findById(id)
                .orElseThrow(() -> new RestApiException(HttpStatus.NOT_FOUND,
                        "Driver licence with id " + id + " not found"));
    }

    /**
     * @param name String - value field in table
     * @return if a record with this field value already exists, it returns an exception with a message,
     * if not, it returns false
     */
    public boolean isDriverLicenceExistsByName(String name) {
        if (driverLicenceRepository.existsByNameIgnoreCase(name)) {
            throw new RestApiException(HttpStatus.CONFLICT,
                    "Driver licence with name " + name + " already exists");
        }
        return false;
    }
}
