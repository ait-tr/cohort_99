package com.competa.competademo.controller;

import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Oleg Karimov
 */
@SpringBootTest
@AutoConfigureMockMvc
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
@DisplayName("UsersController is works: ")
@ExtendWith(MockitoExtension.class)
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JavaMailSender javaMailSender;


    @Nested
    @DisplayName("GET /api/user/me method is works:")
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    class GetMyProfile {

        @WithAnonymousUser
        @Test
        public void getMyProfileAsUnauthorizedUSER() throws Exception {
            mockMvc.perform(get("/api/user/me"))
                    .andExpect(status().isUnauthorized());
        }

        @WithUserDetails(value = "vasja.pupkin@competa.test")
        @Sql(scripts = "/sql/data_for_User_tests.sql")
        @Test
        public void getMyProfileAsADMIN() throws Exception {
            when(javaMailSender.createMimeMessage())
                    .thenReturn(Mockito.mock(MimeMessage.class));

            mockMvc.perform(get("/api/user/me")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.firstName", is("Vasja")))
                    .andExpect(jsonPath("$.lastName", is("Pupkin")))
                    .andExpect(jsonPath("$.nickName", is("Vasja-Pupkin")))
                    .andExpect(jsonPath("$.email", is("vasja.pupkin@competa.test")))
                    .andExpect(jsonPath("$.roles", hasItem("ROLE_ADMIN")))
                    .andExpect(jsonPath("$.avatarName", nullValue()))
                    .andExpect(jsonPath("$.avatarImageData", nullValue()))
                    .andExpect(status().isOk());
        }

        @WithUserDetails(value = "mark.shulz@competa.test")
        @Sql(scripts = "/sql/data_for_User_tests.sql")
        @Test
        public void getMyProfileAsUSER() throws Exception {

            mockMvc.perform(get("/api/user/me")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id", is(2)))
                    .andExpect(jsonPath("$.firstName", is("Mark")))
                    .andExpect(jsonPath("$.lastName", is("Schulz")))
                    .andExpect(jsonPath("$.nickName", is("Mark-Schulz")))
                    .andExpect(jsonPath("$.email", is("mark.shulz@competa.test")))
                    .andExpect(jsonPath("$.roles", hasItem("ROLE_USER")))
                    .andExpect(jsonPath("$.avatarName", nullValue()))
                    .andExpect(jsonPath("$.avatarImageData", nullValue()))
                    .andExpect(status().isOk());
        }
    }

    @Nested
    @DisplayName("PUT /api/user/password-reset method is works:")
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    class ResetPassword {

        @WithAnonymousUser
        @Test
        public void resetPasswordAsUnauthorizedUSER() throws Exception {
            mockMvc.perform(put("/api/user/password-reset"))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @WithUserDetails(value = "otto.shtirliz@competa.test")
        @Sql(scripts = "/sql/data_for_User_tests.sql")
        public void resetCorrectPassword() throws Exception {
            when(javaMailSender.createMimeMessage())
                    .thenReturn(Mockito.mock(MimeMessage.class));

            mockMvc.perform(put("/api/user/password-reset")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"oldPassword\":\"oldPassword007!\",\"newPassword\":\"newPassword123!\"}"))
                    .andExpect(status().is2xxSuccessful());
        }

        @Test
        @WithUserDetails(value = "otto.shtirliz@competa.test")
        @Sql(scripts = "/sql/data_for_User_tests.sql")
        public void resetIncorrectPassword() throws Exception {
            when(javaMailSender.createMimeMessage())
                    .thenReturn(Mockito.mock(MimeMessage.class));

            mockMvc.perform(put("/api/user/password-reset")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"oldPassword\":\"IncorrectPassword\",\"newPassword\":\"newPassword123!\"}"))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @WithUserDetails(value = "otto.shtirliz@competa.test")
        @Sql(scripts = "/sql/data_for_User_tests.sql")
        public void resetNotValidNewPassword() throws Exception {
            mockMvc.perform(put("/api/user/password-reset")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"oldPassword\":\"oldPassword007!\",\"newPassword\":\"NotValidNewPassword\"}"))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @WithUserDetails(value = "otto.shtirliz@competa.test")
        @Sql(scripts = "/sql/data_for_User_tests.sql")
        public void resetSameNewPassword() throws Exception {
            mockMvc.perform(put("/api/user/password-reset")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"oldPassword\":\"oldPassword007!\",\"newPassword\":\"oldPassword007!\"}"))
                    .andExpect(status().isBadRequest());
        }
    }
}
