package com.competa.competademo.controller.rest_api.api;

import com.competa.competademo.dto.ErrorResponseDto;
import com.competa.competademo.dto.HardSkillDto;
import com.competa.competademo.dto.NewHardSkillDto;
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
        @Tag(name = "Hard-skill")
})
@RequestMapping("/api/hard-skill")
public interface HardSkillApi {
    @Operation(summary = "Get all HardSkills", description = "For ADMIN and USER")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "get all Hard Skills",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = HardSkillDto.class, type = "array"))
                    }),
            @ApiResponse(responseCode = "401", description = "User not authenticated",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    })
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    List<HardSkillDto> getAllHardSkills();

    @Operation(summary = "Add HardSkill", description = "Only for Admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "adding successful",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = HardSkillDto.class))
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
            @ApiResponse(responseCode = "409", description = "HardSkill already exists",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    })
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    HardSkillDto createHardSkill(@Parameter(required = true, description = "HardSkill")
                                 @RequestBody @Valid NewHardSkillDto newHardSkill);

    @Operation(summary = "Update HardSkill", description = "Only for Admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "updating successful",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = HardSkillDto.class))
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
            @ApiResponse(responseCode = "409", description = "HardSkill already exists",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    })
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    HardSkillDto updateHardSkill(@Parameter(required = true, description = "HardSkill-id", example = "1")
                                 @PathVariable("id") long hardSkillId,
                                 @RequestBody @Valid NewHardSkillDto newHardSkill);

    @Operation(summary = "Delete HardSkill", description = "Only for Admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "deletion successful",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = HardSkillDto.class))
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
    HardSkillDto deleteHardSkill(@Parameter(required = true, description = "HardSkill-id", example = "1")
                                 @PathVariable("id") long hardSkillId);
}
