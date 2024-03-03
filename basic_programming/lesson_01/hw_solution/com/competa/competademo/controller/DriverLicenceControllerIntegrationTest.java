package com.competa.competademo.controller;

import com.competa.competademo.dto.NewDriverLicenceDto;
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
@DisplayName("DriverLicenceController is works: ")
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
public class DriverLicenceControllerIntegrationTest {

    private static final NewDriverLicenceDto NEW_DL_DTO = NewDriverLicenceDto.builder()
            .name("B")
            .build();

    private static final NewDriverLicenceDto NEW_DL_NOT_VALID = NewDriverLicenceDto.builder()
            .name("")
            .build();

    private static final NewDriverLicenceDto UPDATE_DL = NewDriverLicenceDto.builder()
            .name("Update - A")
            .build();

    private static final NewDriverLicenceDto UPDATE_DL_NOT_VALID = NewDriverLicenceDto.builder()
            .name("")
            .build();

    private static final NewDriverLicenceDto UPDATE_DL_EXIST = NewDriverLicenceDto.builder()
            .name("C")
            .build();


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    @DisplayName("POST /api/driver-licence is works: ")
    class AddDriverLicenceTest {

        @Test
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "admin", roles = {"ADMIN"})
        public void add_new_driverLicence_positive() throws Exception {

            String body = objectMapper.writeValueAsString(NEW_DL_DTO);

            mockMvc.perform(post("/api/driver-licence")
                            .header("Content-Type", "application/json")
                            .content(body))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.name", is("B")));
        }

        @Test
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "admin", roles = {"ADMIN"})
        public void add_new_driverLicence_negative_not_valid() throws Exception {
            String body = objectMapper.writeValueAsString(NEW_DL_NOT_VALID);

            mockMvc.perform(post("/api/driver-licence")
                            .header("Content-Type", "application/json")
                            .content(body))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void add_new_driverLicence_negative_not_authenticatedUser() throws Exception {
            String body = objectMapper.writeValueAsString(NEW_DL_DTO);

            mockMvc.perform(post("/api/driver-licence")
                            .header("Content-Type", "application/json")
                            .content(body))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "user", roles = {"USER"})
        public void add_new_driverLicence_negative_asUser() throws Exception {

            String body = objectMapper.writeValueAsString(NEW_DL_DTO);

            mockMvc.perform(post("/api/driver-licence")
                            .header("Content-Type", "application/json")
                            .content(body))
                    .andExpect(status().isForbidden());
        }

        @Test
        @Sql(scripts = "/sql/data_for_DriverLicence_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "admin", roles = {"ADMIN"})
        public void add_new_driverLicence_negative_alreadyExist() throws Exception {

            String body = objectMapper.writeValueAsString(NEW_DL_DTO);

            mockMvc.perform(post("/api/driver-licence")
                            .header("Content-Type", "application/json")
                            .content(body))
                    .andExpect(status().isConflict());
        }
    }

    @Nested
    @DisplayName("PUT /api/driver-licence is works: ")
    class UpdateDriverLicenceTest {

        @Test
        @Sql(scripts = "/sql/data_for_DriverLicence_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "admin", roles = {"ADMIN"})
        public void update_exist_driverLicence_positive() throws Exception {

            String body = objectMapper.writeValueAsString(UPDATE_DL);

            mockMvc.perform(put("/api/driver-licence/1")
                            .header("Content-Type", "application/json")
                            .content(body))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.name", is("Update - A")));
        }

        @Test
        @Sql(scripts = "/sql/data_for_DriverLicence_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "admin", roles = {"ADMIN"})
        public void update_exist_driverLicence_negative_not_valid() throws Exception {

            String body = objectMapper.writeValueAsString(UPDATE_DL_NOT_VALID);

            mockMvc.perform(put("/api/driver-licence/1")
                            .header("Content-Type", "application/json")
                            .content(body))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @Sql(scripts = "/sql/data_for_DriverLicence_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void update_exist_driverLicence_negative_not_authenticatedUser() throws Exception {
            String body = objectMapper.writeValueAsString(UPDATE_DL);

            mockMvc.perform(put("/api/driver-licence/1")
                            .header("Content-Type", "application/json")
                            .content(body))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @Sql(scripts = "/sql/data_for_DriverLicence_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "user", roles = {"USER"})
        public void update_exist_driverLicence_negative_asUser() throws Exception {

            String body = objectMapper.writeValueAsString(UPDATE_DL);

            mockMvc.perform(put("/api/driver-licence/1")
                            .header("Content-Type", "application/json")
                            .content(body))
                    .andExpect(status().isForbidden());
        }

        @Test
        @Sql(scripts = "/sql/data_for_DriverLicence_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "admin", roles = {"ADMIN"})
        public void update_not_exist_driverLicence_negative() throws Exception {

            String body = objectMapper.writeValueAsString(UPDATE_DL);

            mockMvc.perform(put("/api/driver-licence/10")
                            .header("Content-Type", "application/json")
                            .content(body))
                    .andExpect(status().isNotFound());
        }

        @Test
        @Sql(scripts = "/sql/data_for_DriverLicence_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "admin", roles = {"ADMIN"})
        public void update_exist_driverLicence_negative_alreadyExist() throws Exception {
            String body = objectMapper.writeValueAsString(UPDATE_DL_EXIST);

            mockMvc.perform(put("/api/driver-licence/1")
                            .header("Content-Type", "application/json")
                            .content(body))
                    .andExpect(status().isConflict());
        }
    }

    @Nested
    @DisplayName("GET /api/driver-licence is works: ")
    class GetDriverLicenceTest {

        @Test
        @Sql(scripts = "/sql/data_for_DriverLicence_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "admin", roles = {"ADMIN"})
        public void get_all_driverLicence_positive_asAdmin() throws Exception {

            mockMvc.perform(get("/api/driver-licence/all")
                            .header("Content-Type", "application/json"))
                    .andExpect(status().isOk());
        }

        @Test
        @Sql(scripts = "/sql/data_for_DriverLicence_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "user", roles = {"USER"})
        public void get_all_driverLicence_positive_asUser() throws Exception {

            mockMvc.perform(get("/api/driver-licence/all")
                            .header("Content-Type", "application/json"))
                    .andExpect(status().isOk());
        }

        @Test
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "admin", roles = {"ADMIN"})
        public void get_all_driverLicence_positive_empty() throws Exception {

            mockMvc.perform(get("/api/driver-licence/all")
                            .header("Content-Type", "application/json"))
                    .andExpect(status().isOk());
        }

        @Test
        @Sql(scripts = "/sql/data_for_DriverLicence_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void get_all_driverLicence_negative_not_authenticatedUser() throws Exception {

            mockMvc.perform(get("/api/driver-licence/all")
                            .header("Content-Type", "application/json"))
                    .andExpect(status().isUnauthorized());
        }

    }

    @Nested
    @DisplayName("DELETE /api/driver-licence is works: ")
    class DeleteDriverLicenceTest {

        @Test
        @Sql(scripts = "/sql/data_for_DriverLicence_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "admin", roles = {"ADMIN"})
        public void delete_edLevel_byId_positive() throws Exception {

            mockMvc.perform(delete("/api/driver-licence/1")
                            .header("Content-Type", "application/json"))
                    .andExpect(status().isOk());
        }

        @Test
        @Sql(scripts = "/sql/data_for_DriverLicence_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void delete_driverLicence_byId_negative_not_authenticatedUser() throws Exception {

            mockMvc.perform(delete("/api/driver-licence/1")
                            .header("Content-Type", "application/json"))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @Sql(scripts = "/sql/data_for_DriverLicence_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "user", roles = {"USER"})
        public void delete_driverLicence_byId_negative_asUser() throws Exception {

            mockMvc.perform(delete("/api/driver-licence/1")
                            .header("Content-Type", "application/json"))
                    .andExpect(status().isForbidden());
        }

        @Test
        @Sql(scripts = "/sql/data_for_DriverLicence_tests.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @WithMockUser(username = "admin", roles = {"ADMIN"})
        public void delete_notExist_driverLicence_byId_negative() throws Exception {

            mockMvc.perform(delete("/api/driver-licence/10")
                            .header("Content-Type", "application/json"))
                    .andExpect(status().isNotFound());
        }
    }
}
