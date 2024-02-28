package com.competa.competademo.controller.rest_api.api;

import com.competa.competademo.dto.ErrorResponseDto;
import com.competa.competademo.dto.IndustryDto;
import com.competa.competademo.dto.NewIndustryDto;
import com.competa.competademo.exceptions.ValidationError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

@RequestMapping("/api/industry")
@Tags(value =
@Tag(name = "Industry"))
public interface IndustryApi {

    @Operation(summary = "Add a new industry", description = "Available to administrator")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Industry added",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = IndustryDto.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Validation errors",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationError.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Not valid value name Industry",
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
            @ApiResponse(responseCode = "409", description = "Industry with that name already exists",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    })
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    IndustryDto addIndustry(@Parameter(required = true, description = "New industry")
                            @RequestBody @Valid NewIndustryDto newIndustry);

    @Operation(summary = "Industry update", description = "Available to administrator")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Industry updated",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = IndustryDto.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Validation errors",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationError.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Not valid value name Industry",
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
            @ApiResponse(responseCode = "404", description = "Industry not found",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    }),
            @ApiResponse(responseCode = "409", description = "Industry with that name already exists",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    })
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    IndustryDto updateIndustry(@Parameter(required = true, description = "Industry ID", example = "1")
                               @PathVariable("id") long id,
                               @RequestBody @Valid NewIndustryDto updateIndustry);

    @Operation(summary = "Get list of industry", description = "Available to ADMIN and USER")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of industries",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = IndustryDto.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Validation errors",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationError.class))
                    }),
            @ApiResponse(responseCode = "401", description = "User not authenticated",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    })
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/all")
    List<IndustryDto> getAllIndustry();

    @Operation(summary = "Delete industry", description = "Available to administrator")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Industry deleted",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = IndustryDto.class))
                    }),
            @ApiResponse(responseCode = "401", description = "User not authenticated",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    }),
            @ApiResponse(responseCode = "403", description = "Access denied for user with email <{0}> and role {1}",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Industry not found",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    })
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    IndustryDto deleteIndustry(@Parameter(required = true, description = "Industry id", example = "1")
                               @PathVariable(name = "id") long id);
}

