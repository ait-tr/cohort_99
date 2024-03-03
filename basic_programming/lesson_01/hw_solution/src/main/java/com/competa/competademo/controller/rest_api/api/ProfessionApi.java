package com.competa.competademo.controller.rest_api.api;

import com.competa.competademo.dto.ErrorResponseDto;
import com.competa.competademo.dto.NewProfessionDto;
import com.competa.competademo.dto.ProfessionDto;
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

@RequestMapping("/api/profession")
@Tags(value =
@Tag(name = "Profession"))
public interface ProfessionApi {

    @Operation(summary = "Add a new profession", description = "Available to administrator and user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Profession added",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ProfessionDto.class))
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
            @ApiResponse(responseCode = "409", description = "Profession with that name already exists",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    })
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ProfessionDto addProfession(@Parameter(required = true, description = "New profession")
                                @RequestBody @Valid NewProfessionDto newProfession);

    @Operation(summary = "Profession update", description = "Available to administrator")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profession updated",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ProfessionDto.class))
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
            @ApiResponse(responseCode = "404", description = "Profession not found",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    }),
            @ApiResponse(responseCode = "409", description = "Profession with that name already exists",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    })
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    ProfessionDto updateProfession(@Parameter(required = true, description = "Profession ID", example = "1")
                                   @PathVariable("id") long id,
                                   @RequestBody @Valid NewProfessionDto updateProfession);

    @Operation(summary = "Get list of profession", description = "Available to all")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of profession",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ProfessionDto.class))
                    }),
            @ApiResponse(responseCode = "401", description = "User not authenticated",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    })
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/all")
    List<ProfessionDto> getAllProfession();

    @Operation(summary = "Delete profession", description = "Available to administrator")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profession deleted",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ProfessionDto.class))
                    }),
            @ApiResponse(responseCode = "401", description = "User not authenticated",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    }),
            @ApiResponse(responseCode = "403", description = "Access denied for user with email <{0}> and role {1}",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Profession not found",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    })
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    ProfessionDto deleteProfession(@Parameter(required = true, description = "Profession id", example = "1")
                                   @PathVariable(name = "id") long id);
}
