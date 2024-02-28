package com.competa.competademo.controller;

import com.competa.competademo.dto.NewEduLevelDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("EduLevelController is works: ")
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
public class EduLevelControllerIntegrationTest {

    private static final NewEduLevelDto NEW_EDU_LEVEL_DTO = NewEduLevelDto.builder()
            .name("Начальное")
            .build();

    private static final NewEduLevelDto NEW_EDUL_NOT_VALID = NewEduLevelDto.builder()
            .name("  ")
            .build();

    private static final NewEduLevelDto UPDATE_EDUL = NewEduLevelDto.builder()
            .name("Update - Начальное")
            .build();

    private static final NewEduLevelDto UPDATE_EDUL_NOT_VALID = NewEduLevelDto.builder()
            .name("")
            .build();

    private static final NewEduLevelDto UPDATE_EDUL_EXIST = NewEduLevelDto.builder()
            .name("Среднее")
            .build();


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    @DisplayName("POST /api/edulevel is works: ")
    class AddEduLevelTest {

        @Test
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "admin", roles = {"ADMIN"})
        public void add_new_eduLevel_positive() throws Exception {

            String body = objectMapper.writeValueAsString(NEW_EDU_LEVEL_DTO);

            mockMvc.perform(post("/api/edu-level")
                            .header("Content-Type", "application/json")
                            .content(body))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.name", is("Начальное")));
        }

        @Test
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "admin", roles = {"ADMIN"})
        public void add_new_eduLevel_negative_not_valid() throws Exception {
            String body = objectMapper.writeValueAsString(NEW_EDUL_NOT_VALID);

            mockMvc.perform(post("/api/edu-level")
                            .header("Content-Type", "application/json")
                            .content(body))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void add_new_eduLevel_negative_not_authenticatedUser() throws Exception {
            String body = objectMapper.writeValueAsString(NEW_EDU_LEVEL_DTO);

            mockMvc.perform(post("/api/edu-level")
                            .header("Content-Type", "application/json")
                            .content(body))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "user", roles = {"USER"})
        public void add_new_eduLevel_negative_asUser() throws Exception {

            String body = objectMapper.writeValueAsString(NEW_EDU_LEVEL_DTO);

            mockMvc.perform(post("/api/edu-level")
                            .header("Content-Type", "application/json")
                            .content(body))
                    .andExpect(status().isForbidden());
        }

        @Test
        @Sql(scripts = "/sql/data_for_EduLevel_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "admin", roles = {"ADMIN"})
        public void add_new_eduLevel_negative_alreadyExist() throws Exception {

            String body = objectMapper.writeValueAsString(NEW_EDU_LEVEL_DTO);

            mockMvc.perform(post("/api/edu-level")
                            .header("Content-Type", "application/json")
                            .content(body))
                    .andExpect(status().isConflict());
        }
    }

    @Nested
    @DisplayName("PUT /api/edulevel is works: ")
    class UpdateEduLevelTest {

        @Test
        @Sql(scripts = "/sql/data_for_EduLevel_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "admin", roles = {"ADMIN"})
        public void update_exist_eduLevel_positive() throws Exception {

            String body = objectMapper.writeValueAsString(UPDATE_EDUL);

            mockMvc.perform(put("/api/edu-level/1")
                            .header("Content-Type", "application/json")
                            .content(body))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.name", is("Update - Начальное")));
        }

        @Test
        @Sql(scripts = "/sql/data_for_EduLevel_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "admin", roles = {"ADMIN"})
        public void update_exist_eduLevel_negative_not_valid() throws Exception {

            String body = objectMapper.writeValueAsString(UPDATE_EDUL_NOT_VALID);

            mockMvc.perform(put("/api/edu-level/1")
                            .header("Content-Type", "application/json")
                            .content(body))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @Sql(scripts = "/sql/data_for_EduLevel_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void update_exist_eduLevel_negative_not_authenticatedUser() throws Exception {
            String body = objectMapper.writeValueAsString(UPDATE_EDUL);

            mockMvc.perform(put("/api/edu-level/1")
                            .header("Content-Type", "application/json")
                            .content(body))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @Sql(scripts = "/sql/data_for_EduLevel_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "user", roles = {"USER"})
        public void update_exist_eduLevel_negative_asUser() throws Exception {

            String body = objectMapper.writeValueAsString(UPDATE_EDUL);

            mockMvc.perform(put("/api/edu-level/1")
                            .header("Content-Type", "application/json")
                            .content(body))
                    .andExpect(status().isForbidden());
        }

        @Test
        @Sql(scripts = "/sql/data_for_EduLevel_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "admin", roles = {"ADMIN"})
        public void update_not_exist_eduLevel_negative() throws Exception {

            String body = objectMapper.writeValueAsString(UPDATE_EDUL);

            mockMvc.perform(put("/api/edu-level/10")
                            .header("Content-Type", "application/json")
                            .content(body))
                    .andExpect(status().isNotFound());
        }

        @Test
        @Sql(scripts = "/sql/data_for_EduLevel_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "admin", roles = {"ADMIN"})
        public void update_exist_eduLevel_negative_alreadyExist() throws Exception {
            String body = objectMapper.writeValueAsString(UPDATE_EDUL_EXIST);

            mockMvc.perform(put("/api/edu-level/1")
                            .header("Content-Type", "application/json")
                            .content(body))
                    .andExpect(status().isConflict());
        }
    }

    @Nested
    @DisplayName("GET /api/edulevel is works: ")
    class GetEduLevelTest {

        @Test
        @Sql(scripts = "/sql/data_for_EduLevel_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "admin", roles = {"ADMIN"})
        public void get_all_eduLevel_positive_asAdmin() throws Exception {

            mockMvc.perform(get("/api/edu-level/all")
                            .header("Content-Type", "application/json"))
                    .andExpect(status().isOk());
        }

        @Test
        @Sql(scripts = "/sql/data_for_EduLevel_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "user", roles = {"USER"})
        public void get_all_eduLevel_positive_asUser() throws Exception {

            mockMvc.perform(get("/api/edu-level/all")
                            .header("Content-Type", "application/json"))
                    .andExpect(status().isOk());
        }

        @Test
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "admin", roles = {"ADMIN"})
        public void get_all_eduLevel_positive_empty() throws Exception {

            mockMvc.perform(get("/api/edu-level/all")
                            .header("Content-Type", "application/json"))
                    .andExpect(status().isOk());
        }

        @Test
        @Sql(scripts = "/sql/data_for_EduLevel_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void get_all_eduLevel_negative_not_authenticatedUser() throws Exception {

            mockMvc.perform(get("/api/edu-level/all")
                            .header("Content-Type", "application/json"))
                    .andExpect(status().isUnauthorized());
        }

    }

    @Nested
    @DisplayName("DELETE /api/edulevel is works: ")
    class DeleteEduLevelTest {

        @Test
        @Sql(scripts = "/sql/data_for_EduLevel_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "admin", roles = {"ADMIN"})
        public void delete_edLevel_byId_positive() throws Exception {

            mockMvc.perform(delete("/api/edu-level/1")
                            .header("Content-Type", "application/json"))
                    .andExpect(status().isOk());
        }

        @Test
        @Sql(scripts = "/sql/data_for_EduLevel_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void delete_eduLevel_byId_negative_not_authenticatedUser() throws Exception {

            mockMvc.perform(delete("/api/edu-level/1")
                            .header("Content-Type", "application/json"))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @Sql(scripts = "/sql/data_for_EduLevel_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "admin", roles = {"ADMIN"})
        public void delete_notExist_eduLevel_byId_negative() throws Exception {

            mockMvc.perform(delete("/api/edu-level/10")
                            .header("Content-Type", "application/json"))
                    .andExpect(status().isNotFound());
        }
    }
}
