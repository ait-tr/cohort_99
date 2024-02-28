package com.competa.competademo.controller.rest_api;

import com.competa.competademo.controller.rest_api.api.AvatarApi;
import com.competa.competademo.dto.ImageInfoDto;
import com.competa.competademo.security.details.AuthenticatedUser;
import com.competa.competademo.service.AvatarService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Konstantin Glazunov
 * created on 17-Jan-24 1
 */
@RestController
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AvatarController implements AvatarApi {

    AvatarService avatarService;
    @Override
    public ImageInfoDto renewUserAvatar(AuthenticatedUser user, MultipartFile file) {
        return (avatarService.renewAvatar(file));
    }
}


