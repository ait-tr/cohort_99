package com.competa.competademo.service;

import com.competa.competademo.dto.ImageInfoDto;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Konstantin Glazunov
 * created on 17-Jan-24 1
 */
public interface AvatarService {

    ImageInfoDto renewAvatar(MultipartFile file);

}
