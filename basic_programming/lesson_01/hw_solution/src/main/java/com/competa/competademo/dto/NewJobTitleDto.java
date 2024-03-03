package com.competa.competademo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Education Level")
public class NewJobTitleDto {

    @NotBlank
    @NonNull
    @Schema(description = "Title of education level", example = "начальное")
    private String name;

}
