package com.competa.competademo.controller.rest_api.api;

import com.competa.competademo.dto.ErrorResponseDto;
import com.competa.competademo.dto.ImageInfoDto;
import com.competa.competademo.security.details.AuthenticatedUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Konstantin Glazunov
 * created on 17-Jan-24 1
 */
@Tags(value = {
        @Tag(name = "Avatars")
})
@PreAuthorize("isAuthenticated()")
@RequestMapping("/api/avatar")
public interface AvatarApi {

    @Operation(summary = "Update Avatar", description = "Available to authenticated users, file size less then 15Mb, " +
            "file is an image MIME type: /image")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Avatar updated successfully"
            ),
            @ApiResponse(responseCode = "401", description = "User not authenticated",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class)),

                    }),
            @ApiResponse(responseCode = "422", description = "The file is not supported",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class)),

                    })
    })

    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    ImageInfoDto renewUserAvatar(@AuthenticationPrincipal AuthenticatedUser user,
                                 @Parameter(content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE))
                         @RequestParam("file") MultipartFile file);

}