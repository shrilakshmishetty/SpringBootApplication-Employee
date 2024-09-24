package com.example.JpaNewAssignment.IntegrationTest;

import com.example.JpaNewAssignment.ServiceImpl.UserService;
import com.example.JpaNewAssignment.Model.Address;
import com.example.JpaNewAssignment.Model.Employee;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc

public class EmployeeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    private static String token;
    private static Employee employee1;
    private static Employee employee2;

//    @Container
//    public static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0.32")
//            .withDatabaseName("employeedatabase")
//            .withUsername("root")
//            .withPassword("root");
//
//    @DynamicPropertySource
//    static void configureProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
//        registry.add("spring.datasource.username", mySQLContainer::getUsername);
//        registry.add("spring.datasource.password", mySQLContainer::getPassword);
//    }

//    @BeforeAll
//    static void beforeAll() throws Exception {
//        mySQLContainer.start();
//    }

    @BeforeEach
    public void setup() throws Exception {
        Address address = new Address();
        address.setAddressName("123 Main St");
        employee1 = new Employee(1, "John Doe", address);
        employee2 = new Employee(2, "Jane Doe", address);
        //token="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJTaGFsaW5pIiwicm9sZXMiOiJbUk9MRV9BZG1pbl0iLCJpYXQiOjE3MjU4NTU0MzksImV4cCI6MTcyNTg1OTAzOX0.nTchO8QH8sCPuT6I9pV0Sh-PhY57vYMZaQcLfL5rQgs";
       // Mockito.when(userService.authenticateAndGenerateToken("Shalini", "Shalini")).thenReturn("dummyToken");
    }

//    @AfterAll
//    static void afterAll() {
//        mySQLContainer.stop();
//    }

    @Test
    @WithMockUser(username = "Shalini", roles = {"Admin"})
    void testSaveEmployeeAdmin() throws Exception {
        mockMvc.perform(post("/employees/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"John Doe\", \"address\": {\"addressName\": \"123 Street\"}}"))
                        //header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string("Employee saved successfully"));
    }

    @Test
    @WithMockUser(username = "Shalini", roles = {"User"})
    void testSaveEmployeeUser() throws Exception {
        mockMvc.perform(post("/employees/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"John Doe\", \"address\": {\"addressName\": \"123 Street\"}}"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "Shalini", roles = {"Admin"})
    void testGetAllEmployeesAdmin() throws Exception {
        // Assuming you have saved employee1 to the database before this test
        mockMvc.perform(get("/employees/getAll"))
                //.header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "Shalini", roles = {"User"})
    void testGetAllEmployeesUser() throws Exception {
        mockMvc.perform(get("/employees/getAll"))
                //.header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "Shalini", roles = {"Admin"})
    void testGetEmployeeByIdAdmin() throws Exception {
        // Assuming you have saved employee2 to the database before this test
        mockMvc.perform(get("/employees/getById/{id}", 2))
                //.header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

    }
    @Test
    @WithMockUser(username = "Shalini", roles = {"User"})
    void testGetEmployeeByIdUser() throws Exception {
        mockMvc.perform(get("/employees/getById/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "Shalini", roles = {"Admin"})
    void deleteEmployeeByIdAdmin() throws Exception {
        mockMvc.perform(delete("/employees/delete/{id}", 20))
                        //.header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(username = "Shalini", roles = {"User"})
    void deleteEmployeeByIdUser() throws Exception {
        mockMvc.perform(delete("/employees/delete/{id}", 20))
                       // .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }
}
