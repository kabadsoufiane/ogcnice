package com.ogcnice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ogcnice.dto.TeamCreateDTO;
import com.ogcnice.dto.TeamDTO;
import com.ogcnice.service.TeamService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TeamController.class)
class TeamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeamService teamService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllTeams_shouldReturn200AndList() throws Exception {
        TeamDTO teamDTO = TeamDTO.builder()
                .id(1L)
                .name("OGC Nice")
                .acronym("OGCN")
                .budget(BigDecimal.valueOf(50000000))
                .players(Collections.emptyList())
                .build();

        Mockito.when(teamService.findAllTeams(any()))
                .thenReturn(new org.springframework.data.domain.PageImpl<>(Collections.singletonList(teamDTO)));

        mockMvc.perform(get("/api/teams?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("OGC Nice"));
    }

    @Test
    void createTeam_shouldReturn201_whenValid() throws Exception {
        TeamCreateDTO createDTO = TeamCreateDTO.builder()
                .name("OGC Nice")
                .acronym("OGCN")
                .budget(BigDecimal.valueOf(50000000))
                .build();

        TeamDTO teamDTO = TeamDTO.builder()
                .id(1L)
                .name("OGC Nice")
                .acronym("OGCN")
                .budget(BigDecimal.valueOf(50000000))
                .players(Collections.emptyList())
                .build();

        Mockito.when(teamService.createTeam(any())).thenReturn(teamDTO);

        mockMvc.perform(post("/api/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("OGC Nice"));
    }

    @Test
    void createTeam_shouldReturn400_whenInvalid() throws Exception {
        TeamCreateDTO createDTO = TeamCreateDTO.builder()
                .name("")
                .acronym("OGCN")
                .build();

        mockMvc.perform(post("/api/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isBadRequest());
    }
}
