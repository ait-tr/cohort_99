package com.competa.competademo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Profession")
public class NewProfessionDto {

    @NotBlank
    @NonNull
    @Schema(description = "Title of profession", example = "инженер")
    private String name;

}
