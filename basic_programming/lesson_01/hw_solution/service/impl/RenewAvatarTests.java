package com.competa.competademo.service.impl;

import com.competa.competademo.dto.ImageInfoDto;
import com.competa.competademo.entity.ImageInfo;
import com.competa.competademo.entity.UserProfile;
import com.competa.competademo.exceptions.NotSupportedImageFileException;
import com.competa.competademo.repository.ImageInfoRepository;
import com.competa.competademo.repository.UserProfileRepository;
import com.competa.competademo.service.FileCheckService;
import com.competa.competademo.service.FilesStorageService;
import com.competa.competademo.service.ImageInfoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

/**
 * @author Konstantin Glazunov
 * created on 23-Jan-24 1
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("RenewAvatar is work: ")
public class RenewAvatarTests {
    @InjectMocks
    AvatarServiceImpl avatarServiceImpl;
    @Mock
    UserServiceImpl userServiceImpl;
    @Mock
    FilesStorageService filesStorageService;
    @Mock
    UserProfileRepository profileRepository;
    @Mock
    ImageInfoService imageInfoService;
    @Mock
    ImageInfoRepository imageInfoRepository;
    @Mock
    FileCheckService fileCheckService;
    @Mock
    UserProfileRepository userProfileRepository;
    @Mock
    UserProfile userProfile;
    @Mock
    ImageInfo oldAvatarImageInfo;
    @Mock
    ImageInfo newAvatarImageInfo;

    @Test
    void renewAvatarPositiveTest() {

        MockMultipartFile file = new MockMultipartFile("file",
                "test.jpg",
                "image/jpeg",
                "test data".getBytes());

        when(fileCheckService.isFileSizeValid(any(), anyInt())).thenReturn(true);
        when(fileCheckService.isImage(file)).thenReturn(true);
        when(userProfileRepository.getAuthUserProfile(any())).thenReturn(userProfile);
        when(userProfile.getProfileAvatar()).thenReturn(oldAvatarImageInfo);
        when(newAvatarImageInfo.getId()).thenReturn(1L);
        when(newAvatarImageInfo.getName()).thenReturn("oldAvatarImageFile.jpeg");
        when(newAvatarImageInfo.getUrl()).thenReturn("/url/oldAvatarImageFile.jpeg");
        when(filesStorageService.save(file)).thenReturn(newAvatarImageInfo);

        ImageInfoDto result = avatarServiceImpl.renewAvatar(file);

        assertNotNull(result);
        assertEquals(result.getName(), newAvatarImageInfo.getName());
        assertEquals(true, fileCheckService.isImage(file));
        assertEquals(true, fileCheckService.isFileSizeValid(file, 15));
        verify(filesStorageService, times(1)).init();
        assertEquals(filesStorageService.save(file), newAvatarImageInfo);
        assertEquals(userProfileRepository.getAuthUserProfile(userServiceImpl), userProfile);
        assertEquals(userProfile.getProfileAvatar(), oldAvatarImageInfo);
        verify(userProfile, times(1)).setProfileAvatar(newAvatarImageInfo);
        verify(profileRepository, times(1)).save(userProfile);
        verify(imageInfoRepository, times(1)).delete(oldAvatarImageInfo);
        verify(filesStorageService, times(1)).delete(imageInfoService.getFileName(oldAvatarImageInfo));
    }

    @Test
    void renewAvatarWithBigFile() {

        MockMultipartFile file = new MockMultipartFile("file",
                "test.jpg",
                "image/jpeg",
                "test data".getBytes());

        when(fileCheckService.isFileSizeValid(any(), anyInt())).thenReturn(false);

        NotSupportedImageFileException exception =
                assertThrows(NotSupportedImageFileException.class,
                        () -> avatarServiceImpl.renewAvatar(file));
        assertEquals("The file size is must be less than 15 Mb", exception.getMessage());
        verify(filesStorageService, never()).init();
        verify(filesStorageService, never()).save(file);
        verify(userProfileRepository, never()).getAuthUserProfile(userServiceImpl);
        verify(userProfile, never()).getProfileAvatar();
        verify(userProfile, never()).setProfileAvatar(newAvatarImageInfo);
        verify(profileRepository, never()).save(userProfile);
        verify(imageInfoRepository, never()).deleteById(oldAvatarImageInfo.getId());
        verify(filesStorageService, never()).delete(imageInfoService.getFileName(oldAvatarImageInfo));
    }

    @Test
    void renewAvatarWithNotImageFile() {

        MockMultipartFile file = new MockMultipartFile("file",
                "test.txt",
                "text/txt",
                "test data".getBytes());

        when(fileCheckService.isFileSizeValid(any(), anyInt())).thenReturn(true);
        when(fileCheckService.isImage(file)).thenReturn(false);
NotSupportedImageFileException exception =
        assertThrows(NotSupportedImageFileException.class, ()-> avatarServiceImpl.renewAvatar(file));
assertEquals("The file is not an MIME type: /image", exception.getMessage());

        verify(filesStorageService, never()).init();
        verify(filesStorageService, never()).save(file);
        verify(userProfileRepository, never()).getAuthUserProfile(userServiceImpl);
        verify(userProfile, never()).getProfileAvatar();
        verify(userProfile, never()).setProfileAvatar(newAvatarImageInfo);
        verify(profileRepository, never()).save(userProfile);
        verify(imageInfoRepository, never()).deleteById(oldAvatarImageInfo.getId());
        verify(filesStorageService, never()).delete(imageInfoService.getFileName(oldAvatarImageInfo));
    }
}
