package com.competa.competademo.service.impl;

import com.competa.competademo.dto.ImageInfoDto;
import com.competa.competademo.entity.ImageInfo;
import com.competa.competademo.entity.UserProfile;
import com.competa.competademo.exceptions.NotSupportedImageFileException;
import com.competa.competademo.repository.ImageInfoRepository;
import com.competa.competademo.repository.UserProfileRepository;
import com.competa.competademo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Konstantin Glazunov
 * created on 17-Jan-24 1
 */
@Service
public class AvatarServiceImpl implements AvatarService {
    @Autowired
    FilesStorageService filesStorageService;
    @Autowired
    UserService userService;
    @Autowired
    UserProfileRepository profileRepository;
    @Autowired
    ImageInfoService imageInfoService;
    @Autowired
    ImageInfoRepository imageInfoRepository;
    @Autowired
    FileCheckService fileCheckService;
    @Autowired
    UserProfileService userProfileService;
    @Autowired
    UserProfileRepository userProfileRepository;

    @Override
    public ImageInfoDto renewAvatar(MultipartFile file) {

        if (!(fileCheckService.isFileSizeValid(file, 15))) {
            throw new NotSupportedImageFileException("The file size is must be less than 15 Mb");
        }
        if (!(fileCheckService.isImage(file))) {
            throw new NotSupportedImageFileException("The file is not an MIME type: /image");
        } else {
            filesStorageService.init();
            ImageInfo newAvatarImageInfo = filesStorageService.save(file);
            UserProfile userProfile = userProfileRepository.getAuthUserProfile(userService);
            ImageInfo oldAvatarImageInfo = userProfile.getProfileAvatar();
            userProfile.setProfileAvatar(newAvatarImageInfo);
            profileRepository.save(userProfile);

            if(oldAvatarImageInfo != null){
                imageInfoRepository.delete(oldAvatarImageInfo);
                filesStorageService.delete(imageInfoService.getFileName(oldAvatarImageInfo));
            }
            return ImageInfoDto.from(newAvatarImageInfo);
        }
    }
}
