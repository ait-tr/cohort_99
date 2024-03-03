package com.competa.competademo.controller.rest_api.api;

import com.competa.competademo.dto.ErrorResponseDto;
import com.competa.competademo.dto.NewUserDto;
import com.competa.competademo.dto.UserDto;
import com.competa.competademo.exceptions.ValidationError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Oleg Karimov
 */
@Tags(value = {
        @Tag(name = "Auth")
})
@RequestMapping("/api/auth")
public interface UserAuthControllerApi {
    @Operation(summary = "Register new user", description = "Register new user in application with default role (USER). Available for all")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Details",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
                    }),
            @ApiResponse(responseCode = "400", description = "User with this email already exists",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Validation errors",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationError.class))
                    })
    })
    @PostMapping("/register")
    UserDto registerUser(@RequestBody @Valid NewUserDto createUserDto);
}
