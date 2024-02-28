package com.competa.competademo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Driver Licence")
public class NewDriverLicenceDto {

    @NotBlank
    @NonNull
    @Schema(description = "Title of driver licence", example = "A")
    private String name;

}
