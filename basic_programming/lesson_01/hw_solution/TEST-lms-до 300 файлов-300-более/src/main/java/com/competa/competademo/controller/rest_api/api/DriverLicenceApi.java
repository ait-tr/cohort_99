package com.competa.competademo.controller.rest_api.api;

import com.competa.competademo.dto.DriverLicenceDto;
import com.competa.competademo.dto.ErrorResponseDto;
import com.competa.competademo.dto.NewDriverLicenceDto;
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

@RequestMapping("/api/driver-licence")
@Tags(value =
@Tag(name = "Driver licence"))
public interface DriverLicenceApi {

    @Operation(summary = "Add a new driver licence type (A, B, C, ...)", description = "Available to administrator")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Driver licence type added",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = DriverLicenceDto.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Not valid value name DriverLicence",
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
            @ApiResponse(responseCode = "409", description = "DriverLicence with that name already exists",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    })
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    DriverLicenceDto addDriverLicence(@Parameter(required = true, description = "New driver licence type (A, B, C, ...)")
                                      @RequestBody @Valid NewDriverLicenceDto newDriverLicense);

    @Operation(summary = "Driver licence type update", description = "Available to administrator")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Driver licence updated",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = DriverLicenceDto.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Not valid value name DriverLicence",
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
            @ApiResponse(responseCode = "404", description = "Driver licence not found",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    }),
            @ApiResponse(responseCode = "409", description = "DriverLicence with that name already exists",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    })
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    DriverLicenceDto updateDriverLicence(@Parameter(required = true, description = "Driver licence ID", example = "1")
                                         @PathVariable("id") long id,
                                         @RequestBody @Valid NewDriverLicenceDto updateDriverLicence);

    @Operation(summary = "Get list of driver license types", description = "Available for any authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of driver Licence",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = DriverLicenceDto.class))
                    }),
            @ApiResponse(responseCode = "401", description = "User not authenticated",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    })
    })

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/all")
    List<DriverLicenceDto> getAllDriverLicence();

    @Operation(summary = "Delete driver license type", description = "Available to administrator")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Driver licence deleted",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = DriverLicenceDto.class))
                    }),
            @ApiResponse(responseCode = "401", description = "User not authenticated",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    }),
            @ApiResponse(responseCode = "403", description = "Access denied for user with email <{0}> and role {1}",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Driver licence not found",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    })
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    DriverLicenceDto deleteDriverLicence(@Parameter(required = true, description = "Driver licence id", example = "1")
                                         @PathVariable(name = "id") long id);
}
