package ru.site.web.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
public class GameControllerTest {

    @Autowired private MockMvc mockMvc;

    @BeforeEach
    void setUp() throws Exception {
        String userJson1 = """
            {
                "login": "testuser",
                "password": "12345"
            }
            """;

        String userJson2 = """
            {
                "login": "testuser2",
                "password": "12345"
            }
            """;

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
    public void testInitEndpointWithValidToken() throws Exception {
        String loginJson = "{\"login\":\"testuser\",\"password\":\"12345\"}";

        MvcResult authResult =
            mockMvc
                .perform(post("/auth")
                             .contentType(MediaType.APPLICATION_JSON)
                             .content(loginJson))
                .andReturn();

        String responseBody = authResult.getResponse().getContentAsString();
        String token = JsonPath.read(responseBody, "$.accessToken");

        String initJson = """
            {
                "gameType": "HVS_H",
                "playerSign": "X"
            }
            """;

        mockMvc
            .perform(post("/init")
                         .header("Authorization", "Bearer " + token)
                         .contentType(MediaType.APPLICATION_JSON)
                         .content(initJson))
            .andExpect(status().isCreated());
    }

	@Test
public void testAddPlayerWithValidToken() throws Exception {
    String loginJson = "{\"login\":\"testuser\",\"password\":\"12345\"}";

    MvcResult authResult =
        mockMvc.perform(post("/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
               .andReturn();

    String responseBody = authResult.getResponse().getContentAsString();
    String token = JsonPath.read(responseBody, "$.accessToken");

    String loginJson2 = "{\"login\":\"testuser2\",\"password\":\"12345\"}";

    MvcResult authResult2 =
        mockMvc.perform(post("/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson2))
               .andReturn();

    String responseBody2 = authResult2.getResponse().getContentAsString();
    String token2 = JsonPath.read(responseBody2, "$.accessToken");

    String initJson = """
        {
            "gameType": "HVS_H",
            "playerSign": "X"
        }
        """;

    MvcResult gameInitResult =
        mockMvc.perform(post("/init")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(initJson))
               .andReturn();

    String gameInitResponse = gameInitResult.getResponse().getContentAsString();
    String gameIdInt = JsonPath.read(gameInitResponse, "$.id");
	String gameId = String.valueOf(gameIdInt);


    mockMvc.perform(get("/add/" + gameId)
                    .header("Authorization", "Bearer " + token2)
                    .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk());
}

    @Test
    public void testAuthAndAvailableEndpointWithValidToken() throws Exception {
        String loginJson = "{\"login\":\"testuser\",\"password\":\"12345\"}";

        MvcResult authResult =
            mockMvc
                .perform(post("/auth")
                             .contentType(MediaType.APPLICATION_JSON)
                             .content(loginJson))
                .andReturn();

        String responseBody = authResult.getResponse().getContentAsString();
        String token = JsonPath.read(responseBody, "$.accessToken");

        mockMvc
            .perform(get("/available")
                         .header("Authorization", "Bearer " + token)
                         .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }


    @Test
    public void testAuthAndCompletedEndpointWithValidToken() throws Exception {
        String loginJson = "{\"login\":\"testuser\",\"password\":\"12345\"}";

        MvcResult authResult =
            mockMvc
                .perform(post("/auth")
                             .contentType(MediaType.APPLICATION_JSON)
                             .content(loginJson))
                .andReturn();

        String responseBody = authResult.getResponse().getContentAsString();
        String token = JsonPath.read(responseBody, "$.accessToken");

        mockMvc
            .perform(get("/completed/1")
                         .header("Authorization", "Bearer " + token)
                         .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

}