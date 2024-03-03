package com.competa.competademo.controller;

import com.competa.competademo.entity.ImageInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Konstantin Glazunov
 */
@SpringBootTest
@AutoConfigureMockMvc
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
@DisplayName("UsersController is works: ")
@ExtendWith(MockitoExtension.class)
public class AvatarControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Nested
    @DisplayName("POST /api/avatar/upload method is work")
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    class renewAvatar {
        private Path jpgFilePath = Paths.get("src/test/resources/files/avatar.jpeg");
        private Path falseJpgFilePath = Paths.get("src/test/resources/files/falseJpgFile.jpg");
        private Path bigSizeFilePath = Paths.get("src/test/resources/files/avatarBig.jpg");
        private Path notImageFilePath = Paths.get("src/test/resources/files/simpleTxtFile.txt");
        ObjectMapper objectMapper = new ObjectMapper();
        ImageInfo imageInfo;

        @AfterEach
        void tearDown() {
            if (this.imageInfo != null) {
                try {
                    Path path = Paths.get(imageInfo.getUrl());
                    Files.delete(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @WithAnonymousUser
        @Test
        public void renewAvatarUnauthorizedUSER() throws Exception {
            mockMvc.perform(get("/api/avatar/upload"))
                    .andExpect(status().isUnauthorized());
        }

        @WithUserDetails(value = "otto.shtirliz@competa.test")
        @Sql(scripts = "/sql/data_for_Avatar_tests.sql")
        @Test
        public void renewAvatarPositiveTest() throws Exception {
            MockMultipartFile file = new MockMultipartFile(
                    "file",
                    "avatar.jpg",
                    MediaType.MULTIPART_FORM_DATA_VALUE,
                    Files.readAllBytes(jpgFilePath)
            );
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.multipart("/api/avatar/upload")
                            .file(file))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            String contentResult = result.getResponse().getContentAsString();
            imageInfo = objectMapper.readValue(contentResult, ImageInfo.class);
        }

        @WithUserDetails(value = "otto.shtirliz@competa.test")
        @Sql(scripts = "/sql/data_for_Avatar_tests.sql")
        @Test
        public void renewAvatarWithFalseJpg() throws Exception {
            MockMultipartFile file = new MockMultipartFile(
                    "file",
                    "falseJpgFile.jpg",
                    MediaType.MULTIPART_FORM_DATA_VALUE,
                    Files.readAllBytes(falseJpgFilePath)
            );
            mockMvc.perform(MockMvcRequestBuilders.multipart("/api/avatar/upload")
                            .file(file))
                    .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
        }

        @WithUserDetails(value = "otto.shtirliz@competa.test")
        @Sql(scripts = "/sql/data_for_Avatar_tests.sql")
        @Test
        public void renewAvatarWithBigSizeFile() throws Exception {
            MockMultipartFile file = new MockMultipartFile(
                    "file",
                    "avatarBig.jpg",
                    MediaType.MULTIPART_FORM_DATA_VALUE,
                    Files.readAllBytes(bigSizeFilePath)
            );
            mockMvc.perform(MockMvcRequestBuilders.multipart("/api/avatar/upload")
                            .file(file))
                    .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
        }

        @WithUserDetails(value = "otto.shtirliz@competa.test")
        @Sql(scripts = "/sql/data_for_Avatar_tests.sql")
        @Test
        public void renewAvatarWithNotValidFormatFile() throws Exception {
            MockMultipartFile file = new MockMultipartFile(
                    "file",
                    "simpleTxtFile.txt",
                    MediaType.MULTIPART_FORM_DATA_VALUE,
                    Files.readAllBytes(notImageFilePath)
            );
            mockMvc.perform(MockMvcRequestBuilders.multipart("/api/avatar/upload")
                            .file(file))
                    .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
        }
    }
}
