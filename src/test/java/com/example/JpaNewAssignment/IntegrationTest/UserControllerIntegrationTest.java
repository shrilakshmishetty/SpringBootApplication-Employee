package com.example.JpaNewAssignment.IntegrationTest;

import com.example.JpaNewAssignment.ServiceImpl.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(username = "Shalini", roles = {"Admin"})
    void testRegisterUser() throws Exception {
        // Define the user registration request body with only username and password
        String requestBody = "{ \"userName\": \"Shalini\", \"password\": \"Shalini\" }";
        // Perform the POST request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))  // Use the 'content()' method to send the request body
                .andExpect(status().isOk())  // Expecting HTTP 200 OK for successful registration
                .andExpect(MockMvcResultMatchers.content().string("User registered successfully")); // Verifying the response content
    }
    @Test
    @WithMockUser(username = "Shrii", roles = {"User"})
    void testRegisterAdmin() throws Exception {
        // Define the user registration request body with only username and password
        String requestBody = "{ \"userName\": \"Shrii\", \"password\": \"Shrii\" }";
        // Perform the POST request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))  // Use the 'content()' method to send the request body
                .andExpect(status().isForbidden()) ; // Expecting HTTP 200 OK for successful registration
                // Verifying the response content
    }
    @Test
    void testLoginUserSuccess() throws Exception {
        // Define the input user credentials
        String requestBody = "{ \"username\": \"testuser\", \"password\": \"password123\" }";
        String token = "dummy-jwt-token";
        Mockito.when(userService.authenticateAndGenerateToken("testuser", "password123")).thenReturn(token);

        // Perform the POST request to the /login endpoint and verify the response
        mockMvc.perform(MockMvcRequestBuilders.post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())  // Expecting HTTP 200 OK
                .andExpect(MockMvcResultMatchers.content().string("Bearer " + token));  // Verify the token response
    }
    @Test
    void testLoginUserFailure() throws Exception {
        // Define the input user credentials
        String requestBody = "{ \"username\": \"invaliduser\", \"password\": \"wrongpassword\" }";

        // Mock the userService to throw an exception when the credentials are incorrect
        Mockito.when(userService.authenticateAndGenerateToken("invaliduser", "wrongpassword"))
                .thenThrow(new RuntimeException("Invalid credentials"));

        // Perform the POST request to the /login endpoint and verify the error response
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isUnauthorized());  // Expecting HTTP 401 Unauthorized
             // Verify the error message
    }
}
