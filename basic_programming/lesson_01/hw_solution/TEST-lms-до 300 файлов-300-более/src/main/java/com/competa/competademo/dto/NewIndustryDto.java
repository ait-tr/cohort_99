package com.competa.competademo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Industry")
public class NewIndustryDto {
    @NotBlank
    @NonNull
    @Schema(description = "Industry", example = "education")
    private String name;
}
