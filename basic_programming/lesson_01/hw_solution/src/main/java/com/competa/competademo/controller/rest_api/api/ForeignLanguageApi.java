package com.competa.competademo.controller.rest_api.api;

import com.competa.competademo.dto.ErrorResponseDto;
import com.competa.competademo.dto.ForeignLanguageDto;
import com.competa.competademo.entity.ForeignLanguage;
import com.competa.competademo.exceptions.ValidationError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/language")
@Tags(value =
@Tag(name = "ForeignLanguageApi"))
public interface ForeignLanguageApi {

    @Operation(summary = "Add a new Foreign Language", description = "Available to administrator")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Foreign Language created",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ForeignLanguageDto.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Not valid value name Foreign language",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Validation errors",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationError.class))
                    }),
            @ApiResponse(responseCode = "401", description = "User not authenticated",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    }),
            @ApiResponse(responseCode = "403", description = "Access denied for user with email <{0}> and role {1}",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    }),
            @ApiResponse(responseCode = "409", description = "Foreign language with that name already exists",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    })
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ForeignLanguageDto addForeignLanguage(@RequestBody @Valid ForeignLanguage foreignLanguage);

    @Operation(summary = "Get list of Foreign Language", description = "Available for ADMIN and USER")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of Foreign Language",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ForeignLanguageDto.class))
                    }),
            @ApiResponse(responseCode = "401", description = "User not authenticated",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    })
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/all")
    List<ForeignLanguageDto> getAllForeignLanguage();

    @Operation(summary = "Delete Foreign Language by ID", description = "Available to administrator")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Foreign Language deleted"),
            @ApiResponse(responseCode = "404", description = "Foreign Language not found",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    }),
            @ApiResponse(responseCode = "401", description = "User not authenticated",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    }),
            @ApiResponse(responseCode = "403", description = "Access denied for user with email <{0}> and role {1}",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    }),
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    void deleteForeignLanguage(@PathVariable Long id);

    @Operation(summary = "Update Foreign Language by ID", description = "Available to administrator")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Foreign Language updated",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ForeignLanguageDto.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Validation errors",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationError.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Not valid value name Foreign Language",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Foreign Language not found",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    }),
            @ApiResponse(responseCode = "401", description = "User not authenticated",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    }),
            @ApiResponse(responseCode = "403", description = "Access denied for user with email <{0}> and role {1}",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    }),
            @ApiResponse(responseCode = "409", description = "Foreign Language with that name already exists",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    })
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ForeignLanguageDto updateForeignLanguage(@PathVariable Long id,
                                             @RequestBody @Valid ForeignLanguage foreignLanguage);
}
