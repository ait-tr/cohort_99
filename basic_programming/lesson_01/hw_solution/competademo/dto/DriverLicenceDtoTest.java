package com.competa.competademo.dto;

import com.competa.competademo.entity.Competa;
import com.competa.competademo.entity.DriverLicence;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@ExtendWith(MockitoExtension.class)
@DisplayName("DriverLicenceServiceImpl is works: ")
class DriverLicenceDtoTest {

    @Test
    public void testFromDriverLicense() {
        // Создаём объект eduLevel для теста
        DriverLicence driverLicence = new DriverLicence();
        driverLicence.setName("Test DriverLicence");

        // Вызываем метод from для создания DriverLicenceDto
        DriverLicenceDto driverLicenceDto = DriverLicenceDto.from(driverLicence);

        // Проверка, что созданный объект DriverLicenseDto соответствует ожиданиям
        assertEquals(Long.valueOf(driverLicence.getId()), driverLicenceDto.getId());
        assertEquals(driverLicence.getName(), driverLicenceDto.getName());
    }

    @Test
    public void testListFromDriverLicence() {
        Competa competa = new Competa();
        // Создаём коллекцию объектов DriverLicense для теста
        DriverLicence driverLicence1 = new DriverLicence(1L, "DriverLicence 1",competa);
        DriverLicence driverLicence2 = new DriverLicence(2L, "DriverLicence 2",competa);
        List<DriverLicence> driverLicencesList = Arrays.asList(driverLicence1, driverLicence2);

        // Вызываем метод from для создания списка DriverLicenseDto
        List<DriverLicenceDto> driverLicenceDtoList = DriverLicenceDto.from(driverLicencesList);

        // Проверка, что созданный список DriverLicenseDto соответствует ожиданиям
        assertEquals(driverLicencesList.size(), driverLicenceDtoList.size());

        DriverLicenceDto dto1 = driverLicenceDtoList.get(0);
        assertEquals(Long.valueOf(driverLicence1.getId()), dto1.getId());
        assertEquals(driverLicence1.getName(), dto1.getName());

        DriverLicenceDto dto2 = driverLicenceDtoList.get(1);
        assertEquals(Long.valueOf(driverLicence2.getId()), dto2.getId());
        assertEquals(driverLicence2.getName(), dto2.getName());
    }
}
