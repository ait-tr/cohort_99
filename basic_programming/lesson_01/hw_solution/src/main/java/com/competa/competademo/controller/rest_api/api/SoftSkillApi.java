package com.competa.competademo.controller.rest_api.api;

import com.competa.competademo.dto.ErrorResponseDto;
import com.competa.competademo.dto.NewSoftSkillDto;
import com.competa.competademo.dto.SoftSkillDto;
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

/**
 * @author Oleg Karimov
 */
@Tags(value = {
        @Tag(name = "Soft-skill")
})
@RequestMapping("/api/soft-skill")
public interface SoftSkillApi {
    @Operation(summary = "Get all SoftSkills", description = "Available to ADMIN and USER")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "get all Soft Skills",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = SoftSkillDto.class, type = "array"))
                    }),
            @ApiResponse(responseCode = "401", description = "User not authenticated",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    })
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    List<SoftSkillDto> getAllSoftSkills();

    @Operation(summary = "Add SoftSkill", description = "Only for Admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "adding successful",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = SoftSkillDto.class))
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
            @ApiResponse(responseCode = "409", description = "SoftSkill already exists",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    })
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    SoftSkillDto createSoftSkill(@Parameter(required = true, description = "SoftSkill")
                                 @RequestBody @Valid NewSoftSkillDto newSoftSkill);

    @Operation(summary = "Update SoftSkill", description = "Only for Admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "updating successful",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = SoftSkillDto.class))
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
            @ApiResponse(responseCode = "409", description = "SoftSkill already exists",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    })
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    SoftSkillDto updateSoftSkill(@Parameter(required = true, description = "SoftSkill-id", example = "1")
                                 @PathVariable("id") long softSkillId,
                                 @RequestBody @Valid NewSoftSkillDto newSoftSkill);

    @Operation(summary = "Delete SoftSkill", description = "Only for Admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "deletion successful",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = SoftSkillDto.class))
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
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    SoftSkillDto deleteSoftSkill(@Parameter(required = true, description = "SoftSkill-id", example = "1")
                                 @PathVariable("id") long softSkillId);
}
