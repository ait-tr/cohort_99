package com.competa.competademo.controller;

import com.competa.competademo.dto.NewSoftSkillDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Oleg Karimov
 */
@SpringBootTest
@AutoConfigureMockMvc
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
@DisplayName("SoftSkillController is works: ")
public class SoftSkillControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    @DisplayName("POST /api/soft-skill methods is works: ")
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    class CreateSoftSkillTests {

        @WithMockUser(username = "admin", roles = {"ADMIN"})
        @Test
        void createSoftSkillAsADMIN() throws Exception {
            String body = objectMapper.writeValueAsString(NewSoftSkillDto.builder()
                    .name("Time management")
                    .build());

            mockMvc.perform(post("/api/soft-skill")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(body))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.name", is("Time management")));
        }

        @WithMockUser(username = "user", roles = {"USER"})
        @Test
        void createSoftSkillAsUSER() throws Exception {
            String body = objectMapper.writeValueAsString(NewSoftSkillDto.builder()
                    .name("Time management")
                    .build());

            mockMvc.perform(post("/api/soft-skill")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(body))
                    .andDo(print())
                    .andExpect(status().isForbidden());
        }

        @WithMockUser(username = "admin", roles = {"ADMIN"})
        @Sql(scripts = "/sql/data_for_SoftSkill_tests.sql")
        @Test
        void createSoftSkillAlreadyExists() throws Exception {
            String body = objectMapper.writeValueAsString(NewSoftSkillDto.builder()
                    .name("Teamwork")
                    .build());

            mockMvc.perform(post("/api/soft-skill")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(body))
                    .andDo(print())
                    .andExpect(status().isConflict());
        }

        @WithMockUser(username = "nonexistentUser")
        @Test
        void createSoftSkillAsUnauthorizedUSER() throws Exception {
            String body = objectMapper.writeValueAsString(NewSoftSkillDto.builder()
                    .name("Time management")
                    .build());

            mockMvc.perform(post("/api/soft-skill")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(body))
                    .andDo(print())
                    .andExpect(status().isForbidden());
        }
    }

    @Nested
    @DisplayName("GET /api/soft-skill methods is works: ")
    @Sql(scripts = "/sql/data_for_SoftSkill_tests.sql")
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    class getAllSoftSkillsTests {

        @WithMockUser(username = "user", roles = {"USER"})
        @Test
        public void getAllSoftSkills() throws Exception {
            mockMvc.perform(get("/api/soft-skill")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }
    }

    @Nested
    @DisplayName("PUT /api/soft-skill/{id} methods is works: ")
    @Sql(scripts = "/sql/data_for_SoftSkill_tests.sql")
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    class updateSoftSkillTests {

        @WithMockUser(username = "admin", roles = {"ADMIN"})
        @Sql(scripts = "/sql/data_for_SoftSkill_tests.sql")
        @Test
        void updateSoftSkillAsADMIN() throws Exception {
            String body = objectMapper.writeValueAsString(NewSoftSkillDto.builder()
                    .name("New soft-skill")
                    .build());

            mockMvc.perform(put("/api/soft-skill/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(body))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.name", is("New soft-skill")));
        }

        @WithMockUser(username = "nonexistentUser")
        @Sql(scripts = "/sql/data_for_SoftSkill_tests.sql")
        @Test
        void updateSoftSkillAsUnauthorizedUSER() throws Exception {
            String body = objectMapper.writeValueAsString(NewSoftSkillDto.builder()
                    .name("New soft-skill")
                    .build());

            mockMvc.perform(put("/api/soft-skill/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(body))
                    .andDo(print())
                    .andExpect(status().isForbidden());
        }
    }

    @Nested
    @DisplayName("DELETE /api/soft-skill/{id} methods is works: ")
    @Sql(scripts = "/sql/data_for_SoftSkill_tests.sql")
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    class deleteSoftSkillTests {

        @WithMockUser(username = "admin", roles = {"ADMIN"})
        @Sql(scripts = "/sql/data_for_SoftSkill_tests.sql")
        @Test
        void deleteSoftSkillAsADMIN() throws Exception {
            String body = objectMapper.writeValueAsString(NewSoftSkillDto.builder()
                    .name("Stress-resistant")
                    .build());

            mockMvc.perform(delete("/api/soft-skill/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(body))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.name", is("Stress-resistant")));
        }

        @WithMockUser(username = "nonexistentUser")
        @Sql(scripts = "/sql/data_for_SoftSkill_tests.sql")
        @Test
        void deleteSoftSkillAsUnauthorizedUSER() throws Exception {
            String body = objectMapper.writeValueAsString(NewSoftSkillDto.builder()
                    .name("Stress-resistant")
                    .build());

            mockMvc.perform(put("/api/soft-skill/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(body))
                    .andDo(print())
                    .andExpect(status().isForbidden());
        }
    }
}
