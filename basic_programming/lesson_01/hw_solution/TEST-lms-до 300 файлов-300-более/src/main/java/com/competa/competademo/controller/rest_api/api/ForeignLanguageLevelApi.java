package com.competa.competademo.controller.rest_api.api;

import com.competa.competademo.dto.ErrorResponseDto;
import com.competa.competademo.dto.ForeignLanguageLevelDto;
import com.competa.competademo.entity.ForeignLanguageLevel;
import com.competa.competademo.exceptions.ValidationError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/languageLevel")
@Tags(value =
@Tag(name = "ForeignLanguageApi"))
public interface ForeignLanguageLevelApi {

    @Operation(summary = "Create a new Foreign Language Level", description = "Available to administrator")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Foreign Language Level created",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ForeignLanguageLevelDto.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Validation errors",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationError.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    })
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    ForeignLanguageLevelDto addForeignLanguageLevel(@RequestBody @Valid ForeignLanguageLevel foreignLanguageLevel);

    @Operation(summary = "Get list of Foreign Language Level", description = "Available for ADMIN and USER")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of Foreign Language Level",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ForeignLanguageLevelDto.class))
                    }),
            @ApiResponse(responseCode = "401", description = "User not authenticated",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    })
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/all")
    List<ForeignLanguageLevelDto> getAllForeignLanguageLevel();

    @Operation(summary = "Update Foreign Language Level by ID", description = "Available to administrator")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Foreign Language Level updated",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ForeignLanguageLevelDto.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Validation errors",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationError.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Foreign Language Level not found",
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
            @ApiResponse(responseCode = "409", description = "Foreign language with that name already exists",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    })
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ForeignLanguageLevelDto updateForeignLanguageLevel(@PathVariable Long id,
                                                       @RequestBody @Valid ForeignLanguageLevel foreignLanguageLevel);

    @Operation(summary = "Delete Foreign Language Level by ID", description = "Available to administrator")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Foreign Language Level deleted"),
            @ApiResponse(responseCode = "400", description = "Validation errors",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationError.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Foreign Language Level not found",
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
                    })
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    void deleteForeignLanguageLevel(@PathVariable Long id);
}
