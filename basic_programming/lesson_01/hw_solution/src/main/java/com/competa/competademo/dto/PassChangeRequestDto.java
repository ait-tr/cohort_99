package com.competa.competademo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import static com.competa.competademo.consts.ValidateConstants.passwordPattern;
/**
 * @author Konstantin Glazunov
 */
@Schema(description = "Password Change Request")
public record PassChangeRequestDto(
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @NotEmpty(message = "Password should not be empty")
        @Schema(description = "Old password", example = "Qwerty007!")
        String oldPassword,
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @NotEmpty(message = "Password should not be empty")
        @Pattern(regexp = passwordPattern)
        @Schema(description = "New password", example = "Qwerty008!")
        String newPassword) {
}
