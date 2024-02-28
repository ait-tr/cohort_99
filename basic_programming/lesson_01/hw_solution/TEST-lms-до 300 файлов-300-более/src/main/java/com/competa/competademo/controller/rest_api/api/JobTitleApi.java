package com.competa.competademo.controller.rest_api.api;

import com.competa.competademo.dto.ErrorResponseDto;
import com.competa.competademo.dto.JobTitleDto;
import com.competa.competademo.dto.NewJobTitleDto;
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

@RequestMapping("/api/job-title")
@Tags(value =
@Tag(name = "Title of job"))
public interface JobTitleApi {

    @Operation(summary = "Add a new title of job", description = "Available to ADMIN and USER")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Title of job added",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = JobTitleDto.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Validation errors",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationError.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Not valid value name JobTitle",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    }),
            @ApiResponse(responseCode = "401", description = "User not authenticated",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    }),
            @ApiResponse(responseCode = "409", description = "JobTitle with that name already exists",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    })
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    JobTitleDto addJobTitle(@Parameter(required = true, description = "New title of job")
                            @RequestBody @Valid NewJobTitleDto newJobTitle);

    @Operation(summary = "Update title of job", description = "Available to administrator")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Title of job updated",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = JobTitleDto.class))
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
            @ApiResponse(responseCode = "404", description = "Title of job not found",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    }),
            @ApiResponse(responseCode = "409", description = "JobTitle with that name already exists",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    })
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    JobTitleDto updateJobTitle(@Parameter(required = true, description = "Title of job ID", example = "1")
                               @PathVariable("id") long id,
                               @RequestBody @Valid NewJobTitleDto updateJobTitle);

    @Operation(summary = "Get list title of job", description = "Available to ADMIN and USER")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of Title of job",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = JobTitleDto.class))
                    }),
            @ApiResponse(responseCode = "401", description = "User not authenticated",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    })
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/all")
    List<JobTitleDto> getAllJobTitle();

    @Operation(summary = "Delete title of job", description = "Available to administrator")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Title of job deleted",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = JobTitleDto.class))
                    }),
            @ApiResponse(responseCode = "401", description = "User not authenticated",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    }),
            @ApiResponse(responseCode = "403", description = "Access denied for user with email <{0}> and role {1}",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Title of job not found",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    })
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    JobTitleDto deleteJobTitle(@Parameter(required = true, description = "Title of job id", example = "1")
                               @PathVariable(name = "id") long id);
}
