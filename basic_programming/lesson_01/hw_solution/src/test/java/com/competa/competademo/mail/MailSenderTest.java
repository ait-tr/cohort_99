package com.competa.competademo.mail;

import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@DisplayName("MailService is works: ")
public class MailSenderTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JavaMailSender javaMailSender;
    @Mock
    private MimeMessage mimeMessage;

    @Nested
    @DisplayName("GET/api/user/email-confirmation/{confirm-code}:")
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    @Sql(scripts = "/sql/data_for_Mail_tests.sql")
    public class GetConfirm {


        @Test
        public void return_201_email_confirm() throws Exception {
            Mockito.when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
            mockMvc.perform(get("/api/user/email-confirmation/12345"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.email", is("vasja.pupkin22@competa.test")))
                    .andExpect(jsonPath("$.userStatus", is("CONFIRMED")))
                    .andExpect(jsonPath("$.nickName", is("Vasja-Pupkin-22")));
        }

        @Test
        public void return_404_code_time_is_expired_or_not_found() throws Exception {
            mockMvc.perform(get("/api/user/email-confirmation/12"))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message", is("Code time is expired or not found")));
        }

        @Test
        public void return_409_code_was_used() throws Exception {
            mockMvc.perform(get("/api/user/email-confirmation/6789"))
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.message", is("This code was used, please make new code")));
        }
    }
}
