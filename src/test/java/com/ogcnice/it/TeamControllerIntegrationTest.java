package com.ogcnice.it;

import com.ogcnice.entity.Team;
import com.ogcnice.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.PostgreSQLContainer;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
class TeamControllerIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TeamRepository teamRepository;

    @BeforeEach
    void setUp() {
        teamRepository.deleteAll();
    }

    @Test
    void createTeam_shouldReturn201_andPersistTeam() throws Exception {
        String teamJson = """
        {
          "name": "OGC Nice",
          "acronym": "OGCN",
          "budget": 50000000.00,
          "players": [
            { "name": "Jean Dupont", "position": "DEFENDER" }
          ]
        }
        """;

        mockMvc.perform(post("/api/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(teamJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("OGC Nice"))
                .andExpect(jsonPath("$.players[0].name").value("Jean Dupont"));
    }

    @Test
    void getAllTeams_shouldReturnPagedTeams() throws Exception {
        Team team = Team.builder()
                .name("OGC Nice")
                .acronym("OGCN")
                .budget(BigDecimal.valueOf(50000000))
                .build();
        teamRepository.save(team);

        mockMvc.perform(get("/api/teams?page=0&size=5&sort=name,asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("OGC Nice"));
    }

    @Test
    void createTeam_shouldReturn400_whenMissingBudget() throws Exception {
        String teamJson = """
        {
          "name": "OGC Nice",
          "acronym": "OGCN"
        }
        """;
        mockMvc.perform(post("/api/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(teamJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createTeam_shouldReturn409_whenDuplicateName() throws Exception {
        Team team = Team.builder()
                .name("OGC Nice")
                .acronym("OGCN")
                .budget(BigDecimal.valueOf(50000000))
                .build();
        teamRepository.save(team);

        String teamJson = """
        {
          "name": "OGC Nice",
          "acronym": "OGCN",
          "budget": 50000000.00
        }
        """;
        mockMvc.perform(post("/api/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(teamJson))
                .andExpect(status().isConflict());
    }
}
