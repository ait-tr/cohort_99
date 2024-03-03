package com.competa.competademo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Message from the server. Errors and statuses.")
public class ErrorResponseDto {

    @Schema(description = "Message Text")
    private String message;
}
