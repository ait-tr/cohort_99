package com.competa.competademo.controller.rest_api.api;

import com.competa.competademo.dto.ErrorResponseDto;
import com.competa.competademo.dto.PassChangeRequestDto;
import com.competa.competademo.dto.UserDto;
import com.competa.competademo.security.details.AuthenticatedUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * @author Oleg Karimov
 */
@Tags(value = {
        @Tag(name = "User")
})
@RequestMapping("/api/user")
public interface UserApi {

    @Operation(summary = "Getting profile", description = "Available to authenticated users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Profile",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
                    }),
            @ApiResponse(responseCode = "401", description = "User not authenticated",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    })
    })
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    UserDto getMyProfile(@AuthenticationPrincipal AuthenticatedUser user);

    @Operation(summary = "Reset User Password", description = "Available to authenticated users. " +
            "New password must not be the same as the old password and must contain at least " +
            "8 characters, including at least one digit, " +
            "one lowercase letter, one uppercase letter, and one special character.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "401", description = "User not authenticated",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Invalid new password format, or New password is equals old password, or" +
                    " Incorrect old password.",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    })
    })
    @PutMapping("/password-reset")
    @PreAuthorize("isAuthenticated()")
    void resetPassword(@RequestBody PassChangeRequestDto passChangeRequestDto);


    @Operation(summary = "Confirm email", description = "Confirm user email, change status from NOT_CONFIRMED to CONFIRMED")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "email was confirm",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Code time is expired or not found",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    }),
            @ApiResponse(responseCode = "409", description = "This code was used, please make new code",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    }),
            @ApiResponse(responseCode = "404", description = "User with this code not found" ,
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    }),
    })
    @GetMapping("/email-confirmation/{confirm-code}")
    @PreAuthorize("permitAll()")
    UserDto getConfirmation(@PathVariable("confirm-code") String code);
}
