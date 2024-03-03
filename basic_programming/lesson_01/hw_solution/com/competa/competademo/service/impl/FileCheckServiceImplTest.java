package com.competa.competademo.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
/**
 * @author Konstantin Glazunov
 * created on 25-Jan-24 1
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("FileCheckService is work")
class FileCheckServiceImplTest {
    @InjectMocks
    FileCheckServiceImpl fileCheckService;

    @Test
    void isImagePositiveTest() throws IOException {

        MockMultipartFile file = new MockMultipartFile("file",
                    "avatar.jpeg",
                    "image/jpeg",
                    getClass().getResourceAsStream("/files/avatar.jpeg").readAllBytes()
            );
        assertEquals( true, fileCheckService.isImage(file));
    }

    @Test
    void isImageWithNotImageFile() throws IOException {

        MockMultipartFile file = new MockMultipartFile("file",
                "simpleTxtFile.txt",
                "text/plain",
                getClass().getResourceAsStream("/files/simpleTxtFile.txt").readAllBytes()
        );
        assertEquals( false, fileCheckService.isImage(file));
    }

    @Test
    void isImageWithWrongJpgFile() throws IOException {

        MockMultipartFile file = new MockMultipartFile("file",
                "falseJpgFile.jpg",
                "text/plain",
                getClass().getResourceAsStream("/files/falseJpgFile.jpg").readAllBytes()
        );
        assertEquals( false, fileCheckService.isImage(file));
    }

    @Test
    void isFileSizeValidPositive() throws IOException {

        MockMultipartFile file = new MockMultipartFile("file",
                "avatar.jpeg",
                "image/jpeg",
                getClass().getResourceAsStream("/files/avatar.jpeg").readAllBytes()
        );
        assertEquals(true, fileCheckService.isFileSizeValid(file, 15));

    }

    @Test
    void isFileSizeValidWithBigSize() throws IOException {

        MockMultipartFile file = new MockMultipartFile("file",
                "avatarBig.jpg",
                "image/jpeg",
                getClass().getResourceAsStream("/files/avatarBig.jpg").readAllBytes()
        );
        assertEquals(false, fileCheckService.isFileSizeValid(file, 15));
    }

}