package com.competa.competademo.controller;

import com.competa.competademo.dto.NewProfessionDto;
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
@DisplayName("ProfessionController is works: ")
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
public class ProfessionControllerIntegrationTest {

    private static final NewProfessionDto NEW_PROFESSION_DTO = NewProfessionDto.builder()
            .name("Программист")
            .build();

    private static final NewProfessionDto NEW_PROFESSION_NOT_VALID = NewProfessionDto.builder()
            .name("")
            .build();

    private static final NewProfessionDto UPDATE_PROFESSION = NewProfessionDto.builder()
            .name("Update - Программист")
            .build();

    private static final NewProfessionDto UPDATE_PROFESSION_NOT_VALID = NewProfessionDto.builder()
            .name("")
            .build();

    private static final NewProfessionDto UPDATE_PROFESSION_EXIST = NewProfessionDto.builder()
            .name("Тестировщик")
            .build();


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    @DisplayName("POST /api/profession is works: ")
    class AddProfessionTest {

        @Test
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "admin", roles = {"ADMIN"})
        public void add_new_profession_positive_asAdmin() throws Exception {

            String body = objectMapper.writeValueAsString(NEW_PROFESSION_DTO);

            mockMvc.perform(post("/api/profession")
                            .header("Content-Type", "application/json")
                            .content(body))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.name", is("Программист")));
        }

        @Test
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "user", roles = {"USER"})
        public void add_new_profession_positive_asUser() throws Exception {

            String body = objectMapper.writeValueAsString(NEW_PROFESSION_DTO);

            mockMvc.perform(post("/api/profession")
                            .header("Content-Type", "application/json")
                            .content(body))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.name", is("Программист")));
        }
        
        @Test
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "admin", roles = {"ADMIN"})
        public void add_new_profession_negative_not_valid() throws Exception {
            String body = objectMapper.writeValueAsString(NEW_PROFESSION_NOT_VALID);

            mockMvc.perform(post("/api/profession")
                            .header("Content-Type", "application/json")
                            .content(body))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void add_new_profession_negative_not_authenticatedUser() throws Exception {
            String body = objectMapper.writeValueAsString(NEW_PROFESSION_DTO);

            mockMvc.perform(post("/api/profession")
                            .header("Content-Type", "application/json")
                            .content(body))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @Sql(scripts = "/sql/data_for_Profession_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "admin", roles = {"ADMIN"})
        public void add_new_profession_negative_alreadyExist() throws Exception {

            String body = objectMapper.writeValueAsString(NEW_PROFESSION_DTO);

            mockMvc.perform(post("/api/profession")
                            .header("Content-Type", "application/json")
                            .content(body))
                    .andExpect(status().isConflict());
        }
    }

    @Nested
    @DisplayName("PUT /api/profession is works: ")
    class UpdateProfessionTest {

        @Test
        @Sql(scripts = "/sql/data_for_Profession_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "admin", roles = {"ADMIN"})
        public void update_exist_profession_positive() throws Exception {

            String body = objectMapper.writeValueAsString(UPDATE_PROFESSION);

            mockMvc.perform(put("/api/profession/1")
                            .header("Content-Type", "application/json")
                            .content(body))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.name", is("Update - Программист")));
        }

        @Test
        @Sql(scripts = "/sql/data_for_Profession_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "admin", roles = {"ADMIN"})
        public void update_exist_profession_negative_not_valid() throws Exception {

            String body = objectMapper.writeValueAsString(UPDATE_PROFESSION_NOT_VALID);

            mockMvc.perform(put("/api/profession/1")
                            .header("Content-Type", "application/json")
                            .content(body))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @Sql(scripts = "/sql/data_for_Profession_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void update_exist_profession_negative_not_authenticatedUser() throws Exception {
            String body = objectMapper.writeValueAsString(UPDATE_PROFESSION);

            mockMvc.perform(put("/api/profession/1")
                            .header("Content-Type", "application/json")
                            .content(body))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @Sql(scripts = "/sql/data_for_Profession_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "user", roles = {"USER"})
        public void update_exist_profession_negative_asUser() throws Exception {

            String body = objectMapper.writeValueAsString(UPDATE_PROFESSION);

            mockMvc.perform(put("/api/profession/1")
                            .header("Content-Type", "application/json")
                            .content(body))
                    .andExpect(status().isForbidden());
        }

        @Test
        @Sql(scripts = "/sql/data_for_Profession_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "admin", roles = {"ADMIN"})
        public void update_not_exist_profession_negative() throws Exception {

            String body = objectMapper.writeValueAsString(UPDATE_PROFESSION);

            mockMvc.perform(put("/api/profession/10")
                            .header("Content-Type", "application/json")
                            .content(body))
                    .andExpect(status().isNotFound());
        }

        @Test
        @Sql(scripts = "/sql/data_for_Profession_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "admin", roles = {"ADMIN"})
        public void update_exist_profession_negative_alreadyExist() throws Exception {
            String body = objectMapper.writeValueAsString(UPDATE_PROFESSION_EXIST);

            mockMvc.perform(put("/api/profession/1")
                            .header("Content-Type", "application/json")
                            .content(body))
                    .andExpect(status().isConflict());
        }
    }

    @Nested
    @DisplayName("GET /api/profession is works: ")
    class GetProfessionTest {

        @Test
        @Sql(scripts = "/sql/data_for_Profession_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "admin", roles = {"ADMIN"})
        public void get_all_profession_positive_asAdmin() throws Exception {

            mockMvc.perform(get("/api/profession/all")
                            .header("Content-Type", "application/json"))
                    .andExpect(status().isOk());
        }

        @Test
        @Sql(scripts = "/sql/data_for_Profession_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "user", roles = {"USER"})
        public void get_all_profession_positive_asUser() throws Exception {

            mockMvc.perform(get("/api/profession/all")
                            .header("Content-Type", "application/json"))
                    .andExpect(status().isOk());
        }

        @Test
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "admin", roles = {"ADMIN"})
        public void get_all_profession_positive_empty() throws Exception {

            mockMvc.perform(get("/api/profession/all")
                            .header("Content-Type", "application/json"))
                    .andExpect(status().isOk());
        }

        @Test
        @Sql(scripts = "/sql/data_for_Profession_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void get_all_profession_negative_not_authenticatedUser() throws Exception {

            mockMvc.perform(get("/api/profession/all")
                            .header("Content-Type", "application/json"))
                    .andExpect(status().isUnauthorized());
        }

    }

    @Nested
    @DisplayName("DELETE /api/profession is works: ")
    class DeleteProfessionTest {

        @Test
        @Sql(scripts = "/sql/data_for_Profession_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "admin", roles = {"ADMIN"})
        public void delete_edLevel_byId_positive() throws Exception {

            mockMvc.perform(delete("/api/profession/1")
                            .header("Content-Type", "application/json"))
                    .andExpect(status().isOk());
        }

        @Test
        @Sql(scripts = "/sql/data_for_Profession_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void delete_profession_byId_negative_not_authenticatedUser() throws Exception {

            mockMvc.perform(delete("/api/profession/1")
                            .header("Content-Type", "application/json"))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @Sql(scripts = "/sql/data_for_Profession_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "user", roles = {"USER"})
        public void delete_profession_byId_negative_asUser() throws Exception {

            mockMvc.perform(delete("/api/profession/1")
                            .header("Content-Type", "application/json"))
                    .andExpect(status().isForbidden());
        }

        @Test
        @Sql(scripts = "/sql/data_for_Profession_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "admin", roles = {"ADMIN"})
        public void delete_notExist_profession_byId_negative() throws Exception {

            mockMvc.perform(delete("/api/profession/10")
                            .header("Content-Type", "application/json"))
                    .andExpect(status().isNotFound());
        }
    }
}
