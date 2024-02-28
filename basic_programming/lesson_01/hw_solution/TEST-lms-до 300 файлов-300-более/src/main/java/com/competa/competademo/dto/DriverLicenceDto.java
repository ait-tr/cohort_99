package com.competa.competademo.dto;

import com.competa.competademo.entity.DriverLicence;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Driver licence")
public class DriverLicenceDto {

    @Schema(description = "Driver licence ID", example = "1")
    private Long id;

    @Schema(description = "Title of driver licence", example = "A")
    private String name;

    public static DriverLicenceDto from(DriverLicence driverLicence) {
        return DriverLicenceDto.builder()
                .id(driverLicence.getId())
                .name(driverLicence.getName())
                .build();
    }

    public static List<DriverLicenceDto> from(List<DriverLicence> driverLicences) {
        return driverLicences.stream()
                .map(DriverLicenceDto::from)
                .collect(Collectors.toList());
    }

}
