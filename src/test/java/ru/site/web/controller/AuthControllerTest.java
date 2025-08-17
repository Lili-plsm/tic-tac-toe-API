package ru.site.web.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional

public class AuthControllerTest {

    @Autowired private MockMvc mockMvc;

	private static 
	    String userJson1 = """
            {
                "login": "testuser",
                "password": "12345"
            }
            """;

        private static  String userJson2 = """
            {
                "login": "testuser2",
                "password": "12345"
            }
            """;

	@BeforeEach
    void setUp() throws Exception {
    

        mockMvc.perform(post("/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(userJson1)
                            .with(csrf()));

        mockMvc.perform(post("/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(userJson2)
                            .with(csrf()));
    }

    @Test
    public void testRegisterSuccess() throws Exception {
        String userJson = """
            {
                "login": "testuser3",
                "password": "12345"
            }
            """;

        mockMvc
            .perform(post("/register")
                         .contentType(MediaType.APPLICATION_JSON)
                         .content(userJson)
                         .with(csrf()))
            .andExpect(status().isCreated());
    }

    @Test
    public void testRegisterFailUserAlreadyExists() throws Exception {
        String userJson = """
            {
                "login": "testuser",
                "password": "12345"
            }
            """;

        mockMvc
            .perform(post("/register")
                         .contentType(MediaType.APPLICATION_JSON)
                         .content(userJson)
                         .with(csrf()))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").value(
                "User with this login already exists"));
    }

    @Test
    public void testAuthAndInfoEndpointWithValidToken() throws Exception {

        MvcResult authResult =
            mockMvc
                .perform(post("/auth")
                             .contentType(MediaType.APPLICATION_JSON)
                             .content(userJson1))
                .andReturn();

        String responseBody = authResult.getResponse().getContentAsString();
        String token = JsonPath.read(responseBody, "$.accessToken");

        mockMvc
            .perform(get("/info")
                         .header("Authorization", "Bearer " + token)
                         .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
}