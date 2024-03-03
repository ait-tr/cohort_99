package com.competa.competademo.controller;

import com.competa.competademo.dto.NewJobTitleDto;
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
@DisplayName("JobTitleController is works: ")
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
public class JobTitleControllerIntegrationTest {

    private static final NewJobTitleDto NEW_JT_DTO = NewJobTitleDto.builder()
            .name("Программист")
            .build();

    private static final NewJobTitleDto NEW_JT_NOT_VALID = NewJobTitleDto.builder()
            .name("")
            .build();

    private static final NewJobTitleDto UPDATE_JT = NewJobTitleDto.builder()
            .name("Update - Программист")
            .build();

    private static final NewJobTitleDto UPDATE_JT_NOT_VALID = NewJobTitleDto.builder()
            .name("")
            .build();

    private static final NewJobTitleDto UPDATE_JT_EXIST = NewJobTitleDto.builder()
            .name("Тестировщик")
            .build();


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    @DisplayName("POST /api/job-title is works: ")
    class AddJobTitleTest {

        @Test
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "admin", roles = {"ADMIN"})
        public void add_new_jobTitle_positive_asAdmin() throws Exception {

            String body = objectMapper.writeValueAsString(NEW_JT_DTO);

            mockMvc.perform(post("/api/job-title")
                            .header("Content-Type", "application/json")
                            .content(body))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.name", is("Программист")));
        }

        @Test
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "user", roles = {"USER"})
        public void add_new_jobTitle_positive_asUser() throws Exception {

            String body = objectMapper.writeValueAsString(NEW_JT_DTO);

            mockMvc.perform(post("/api/job-title")
                            .header("Content-Type", "application/json")
                            .content(body))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.name", is("Программист")));
        }

        @Test
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "admin", roles = {"ADMIN"})
        public void add_new_jobTitle_negative_not_valid() throws Exception {
            String body = objectMapper.writeValueAsString(NEW_JT_NOT_VALID);

            mockMvc.perform(post("/api/job-title")
                            .header("Content-Type", "application/json")
                            .content(body))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void add_new_jobTitle_negative_not_authenticatedUser() throws Exception {
            String body = objectMapper.writeValueAsString(NEW_JT_DTO);

            mockMvc.perform(post("/api/job-title")
                            .header("Content-Type", "application/json")
                            .content(body))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @Sql(scripts = "/sql/data_for_JobTitle_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "admin", roles = {"ADMIN"})
        public void add_new_jobTitle_negative_alreadyExist() throws Exception {

            String body = objectMapper.writeValueAsString(NEW_JT_DTO);

            mockMvc.perform(post("/api/job-title")
                            .header("Content-Type", "application/json")
                            .content(body))
                    .andExpect(status().isConflict());
        }
    }

    @Nested
    @DisplayName("PUT /api/job-title is works: ")
    class UpdateJobTitleTest {

        @Test
        @Sql(scripts = "/sql/data_for_JobTitle_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "admin", roles = {"ADMIN"})
        public void update_exist_jobTitle_positive() throws Exception {

            String body = objectMapper.writeValueAsString(UPDATE_JT);

            mockMvc.perform(put("/api/job-title/1")
                            .header("Content-Type", "application/json")
                            .content(body))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.name", is("Update - Программист")));
        }

        @Test
        @Sql(scripts = "/sql/data_for_JobTitle_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "admin", roles = {"ADMIN"})
        public void update_exist_jobTitle_negative_not_valid() throws Exception {

            String body = objectMapper.writeValueAsString(UPDATE_JT_NOT_VALID);

            mockMvc.perform(put("/api/job-title/1")
                            .header("Content-Type", "application/json")
                            .content(body))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @Sql(scripts = "/sql/data_for_JobTitle_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void update_exist_jobTitle_negative_not_authenticatedUser() throws Exception {
            String body = objectMapper.writeValueAsString(UPDATE_JT);

            mockMvc.perform(put("/api/job-title/1")
                            .header("Content-Type", "application/json")
                            .content(body))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @Sql(scripts = "/sql/data_for_JobTitle_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "user", roles = {"USER"})
        public void update_exist_jobTitle_negative_asUser() throws Exception {

            String body = objectMapper.writeValueAsString(UPDATE_JT);

            mockMvc.perform(put("/api/job-title/1")
                            .header("Content-Type", "application/json")
                            .content(body))
                    .andExpect(status().isForbidden());
        }

        @Test
        @Sql(scripts = "/sql/data_for_JobTitle_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "admin", roles = {"ADMIN"})
        public void update_not_exist_jobTitle_negative() throws Exception {

            String body = objectMapper.writeValueAsString(UPDATE_JT);

            mockMvc.perform(put("/api/job-title/10")
                            .header("Content-Type", "application/json")
                            .content(body))
                    .andExpect(status().isNotFound());
        }

        @Test
        @Sql(scripts = "/sql/data_for_JobTitle_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "admin", roles = {"ADMIN"})
        public void update_exist_jobTitle_negative_alreadyExist() throws Exception {
            String body = objectMapper.writeValueAsString(UPDATE_JT_EXIST);

            mockMvc.perform(put("/api/job-title/1")
                            .header("Content-Type", "application/json")
                            .content(body))
                    .andExpect(status().isConflict());
        }
    }

    @Nested
    @DisplayName("GET /api/job-title is works: ")
    class GetJobTitleTest {

        @Test
        @Sql(scripts = "/sql/data_for_JobTitle_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "admin", roles = {"ADMIN"})
        public void get_all_jobTitle_positive_asAdmin() throws Exception {

            mockMvc.perform(get("/api/job-title/all")
                            .header("Content-Type", "application/json"))
                    .andExpect(status().isOk());
        }

        @Test
        @Sql(scripts = "/sql/data_for_JobTitle_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "user", roles = {"USER"})
        public void get_all_jobTitle_positive_asUser() throws Exception {

            mockMvc.perform(get("/api/job-title/all")
                            .header("Content-Type", "application/json"))
                    .andExpect(status().isOk());
        }

        @Test
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "admin", roles = {"ADMIN"})
        public void get_all_jobTitle_positive_empty() throws Exception {

            mockMvc.perform(get("/api/job-title/all")
                            .header("Content-Type", "application/json"))
                    .andExpect(status().isOk());
        }

        @Test
        @Sql(scripts = "/sql/data_for_JobTitle_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void get_all_jobTitle_negative_not_authenticatedUser() throws Exception {

            mockMvc.perform(get("/api/job-title/all")
                            .header("Content-Type", "application/json"))
                    .andExpect(status().isUnauthorized());
        }

    }

    @Nested
    @DisplayName("DELETE /api/job-title is works: ")
    class DeleteJobTitleTest {

        @Test
        @Sql(scripts = "/sql/data_for_JobTitle_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "admin", roles = {"ADMIN"})
        public void delete_edLevel_byId_positive() throws Exception {

            mockMvc.perform(delete("/api/job-title/1")
                            .header("Content-Type", "application/json"))
                    .andExpect(status().isOk());
        }

        @Test
        @Sql(scripts = "/sql/data_for_JobTitle_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void delete_jobTitle_byId_negative_not_authenticatedUser() throws Exception {

            mockMvc.perform(delete("/api/job-title/1")
                            .header("Content-Type", "application/json"))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @Sql(scripts = "/sql/data_for_JobTitle_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "user", roles = {"USER"})
        public void delete_jobTitle_byId_negative_asUser() throws Exception {

            mockMvc.perform(delete("/api/job-title/1")
                            .header("Content-Type", "application/json"))
                    .andExpect(status().isForbidden());
        }

        @Test
        @Sql(scripts = "/sql/data_for_JobTitle_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "admin", roles = {"ADMIN"})
        public void delete_notExist_jobTitle_byId_negative() throws Exception {

            mockMvc.perform(delete("/api/job-title/10")
                            .header("Content-Type", "application/json"))
                    .andExpect(status().isNotFound());
        }
    }
}
