package com.competa.competademo.controller;


import com.competa.competademo.dto.NewHardSkillDto;
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
@DisplayName("HardSkillController is works: ")
public class HardSkillControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    @DisplayName("POST /api/hard-skill methods is works: ")
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    class CreateHardSkillTests {

        @WithMockUser(username = "admin", roles = {"ADMIN"})
        @Test
        void createHardSkillAsADMIN() throws Exception {
            String body = objectMapper.writeValueAsString(NewHardSkillDto.builder()
                    .name("React user interface (UI) framework knowledge")
                    .build());

            mockMvc.perform(post("/api/hard-skill")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(body))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.name", is("React user interface (UI) framework knowledge")));
        }

        @WithMockUser(username = "user", roles = {"USER"})
        @Test
        void createHardSkillAsUSER() throws Exception {
            String body = objectMapper.writeValueAsString(NewHardSkillDto.builder()
                    .name("React user interface (UI) framework knowledge")
                    .build());

            mockMvc.perform(post("/api/hard-skill")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(body))
                    .andDo(print())
                    .andExpect(status().isForbidden());
        }

        @WithMockUser(username = "admin", roles = {"ADMIN"})
        @Sql(scripts = "/sql/data_for_HardSkill_tests.sql")
        @Test
        void createHardSkillAlreadyExists() throws Exception {
            String body = objectMapper.writeValueAsString(NewHardSkillDto.builder()
                    .name("Java Spring Boot")
                    .build());

            mockMvc.perform(post("/api/hard-skill")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(body))
                    .andDo(print())
                    .andExpect(status().isConflict());
        }

        @WithMockUser(username = "nonexistentUser")
        @Test
        void createHardSkillAsUnauthorizedUSER() throws Exception {
            String body = objectMapper.writeValueAsString(NewHardSkillDto.builder()
                    .name("React user interface (UI) framework knowledge")
                    .build());

            mockMvc.perform(post("/api/hard-skill")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(body))
                    .andDo(print())
                    .andExpect(status().isForbidden());
        }
    }

    @Nested
    @DisplayName("GET /api/hard-skill methods is works: ")
    @Sql(scripts = "/sql/data_for_HardSkill_tests.sql")
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    class getAllHardSkillsTests {

        @WithMockUser(username = "user", roles = {"USER"})
        @Test
        public void getAllHardSkills() throws Exception {
            mockMvc.perform(get("/api/hard-skill")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }
    }

    @Nested
    @DisplayName("PUT /api/hard-skill/{id} methods is works: ")
    @Sql(scripts = "/sql/data_for_HardSkill_tests.sql")
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    class updateHardSkillTests {

        @WithMockUser(username = "admin", roles = {"ADMIN"})
        @Sql(scripts = "/sql/data_for_HardSkill_tests.sql")
        @Test
        void updateHardSkillAsADMIN() throws Exception {
            String body = objectMapper.writeValueAsString(NewHardSkillDto.builder()
                    .name("New Hard-skill")
                    .build());

            mockMvc.perform(put("/api/hard-skill/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(body))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.name", is("New Hard-skill")));
        }

        @WithMockUser(username = "nonexistentUser")
        @Sql(scripts = "/sql/data_for_HardSkill_tests.sql")
        @Test
        void updateHardSkillAsUnauthorizedUSER() throws Exception {
            String body = objectMapper.writeValueAsString(NewHardSkillDto.builder()
                    .name("New Hard-skill")
                    .build());

            mockMvc.perform(put("/api/hard-skill/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(body))
                    .andDo(print())
                    .andExpect(status().isForbidden());
        }
    }

    @Nested
    @DisplayName("DELETE /api/hard-skill/{id} methods is works: ")
    @Sql(scripts = "/sql/data_for_HardSkill_tests.sql")
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    class deleteHardSkillTests {

        @WithMockUser(username = "admin", roles = {"ADMIN"})
        @Sql(scripts = "/sql/data_for_HardSkill_tests.sql")
        @Test
        void deleteHardSkillAsADMIN() throws Exception {
            String body = objectMapper.writeValueAsString(NewHardSkillDto.builder()
                    .name("Java Spring Boot")
                    .build());

            mockMvc.perform(delete("/api/hard-skill/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(body))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.name", is("Java Spring Boot")));
        }

        @WithMockUser(username = "nonexistentUser")
        @Sql(scripts = "/sql/data_for_HardSkill_tests.sql")
        @Test
        void deleteHardSkillAsUnauthorizedUSER() throws Exception {
            String body = objectMapper.writeValueAsString(NewHardSkillDto.builder()
                    .name("Java Spring Boot")
                    .build());

            mockMvc.perform(put("/api/hard-skill/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(body))
                    .andDo(print())
                    .andExpect(status().isForbidden());
        }
    }
}
