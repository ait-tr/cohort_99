package com.competa.competademo.controller;

import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
@DisplayName("Registration new user is works")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(MockitoExtension.class)
public class UserAuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean  // This mocks the JavaMailSender
    private JavaMailSender javaMailSender;

    @Test
    public void registerUser() throws Exception {
        when(javaMailSender.createMimeMessage())
                .thenReturn(Mockito.mock(MimeMessage.class));

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "email": "test@mail.com",
                                  "password": "Qwerty12345!",
                                  "nickName": "testUser"
                                }
                                """))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Sql(scripts = "/sql/data_for_User_tests.sql")
    @Test
    public void registerUserAsAlreadyExists() throws Exception {
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"email\": \"vasja.pupkin@competa.test\",\n" +
                                "  \"password\": \"userPass007!\"\n" +
                                "}"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
