package com.competa.competademo.controller.rest_api.api;

import com.competa.competademo.dto.EduLevelDto;
import com.competa.competademo.dto.ErrorResponseDto;
import com.competa.competademo.dto.NewEduLevelDto;
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

@RequestMapping("/api/edu-level")
@Tags(value =
@Tag(name = "EduLevel"))
public interface EduLevelApi {

    @Operation(summary = "Add a new education level", description = "Available to administrator")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Education level added",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = EduLevelDto.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Not valid value name EduLevel",
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
            @ApiResponse(responseCode = "409", description = "EduLevel with that name already exists",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    })
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    EduLevelDto addEduLevel(@Parameter(required = true, description = "New education level")
                            @RequestBody @Valid NewEduLevelDto newEduLevel);

    @Operation(summary = "Education level update", description = "Available to administrator")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Education level updated",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = EduLevelDto.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Validation errors",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationError.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Not valid value name EduLevel",
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
            @ApiResponse(responseCode = "404", description = "Education level not found",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    }),
            @ApiResponse(responseCode = "409", description = "EduLevel with that name already exists",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    })
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    EduLevelDto updateEduLevel(@Parameter(required = true, description = "Education level ID", example = "1")
                               @PathVariable("id") long id,
                               @RequestBody @Valid NewEduLevelDto updateEduLevel);

    @Operation(summary = "Get list of education level", description = "Available for ADMIN and USER")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of education level",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = EduLevelDto.class))
                    }),
            @ApiResponse(responseCode = "401", description = "User not authenticated",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    })
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/all")
    List<EduLevelDto> getAllEduLevel();

    @Operation(summary = "Delete education level", description = "Available to administrator")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Education level deleted",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = EduLevelDto.class))
                    }),
            @ApiResponse(responseCode = "401", description = "User not authenticated",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    }),
            @ApiResponse(responseCode = "403", description = "Access denied for user with email <{0}> and role {1}",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Education level not found",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    })
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    EduLevelDto deleteEduLevel(@Parameter(required = true, description = "Education level id", example = "1")
                               @PathVariable(name = "id") long id);
}
