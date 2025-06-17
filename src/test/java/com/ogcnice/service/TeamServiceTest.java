package com.ogcnice.service;

import com.ogcnice.dto.TeamCreateDTO;
import com.ogcnice.dto.TeamDTO;
import com.ogcnice.entity.Team;
import com.ogcnice.exception.BusinessException;
import com.ogcnice.mapper.TeamMapper;
import com.ogcnice.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;
    @Mock
    private TeamMapper teamMapper;

    @InjectMocks
    private TeamService teamService;

    @Test
    void createTeam_shouldCreateTeam_whenValidInput() {
        TeamCreateDTO dto = TeamCreateDTO.builder()
                .name("OGC Nice")
                .acronym("OGCN")
                .budget(BigDecimal.valueOf(50000000))
                .build();

        Team team = Team.builder()
                .name("OGC Nice")
                .acronym("OGCN")
                .budget(BigDecimal.valueOf(50000000))
                .build();

        when(teamRepository.existsByName("OGC Nice")).thenReturn(false);
        when(teamRepository.existsByAcronym("OGCN")).thenReturn(false);
        when(teamMapper.toEntity(dto)).thenReturn(team);
        when(teamRepository.save(any(Team.class))).thenReturn(team);
        when(teamMapper.toDto(team)).thenReturn(
                TeamDTO.builder().name("OGC Nice").acronym("OGCN").budget(BigDecimal.valueOf(50000000)).build()
        );

        TeamDTO result = teamService.createTeam(dto);

        assertThat(result.getName()).isEqualTo("OGC Nice");
        assertThat(result.getAcronym()).isEqualTo("OGCN");
        assertThat(result.getBudget()).isEqualTo(BigDecimal.valueOf(50000000));
        verify(teamRepository).save(team);
    }

    @Test
    void createTeam_shouldThrowException_whenNameExists() {
        TeamCreateDTO dto = TeamCreateDTO.builder()
                .name("OGC Nice").acronym("OGCN").budget(BigDecimal.valueOf(1000)).build();
        when(teamRepository.existsByName("OGC Nice")).thenReturn(true);

        assertThatThrownBy(() -> teamService.createTeam(dto))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Nom d'équipe déjà utilisé");
    }

    @Test
    void createTeam_shouldThrowException_whenAcronymExists() {
        TeamCreateDTO dto = TeamCreateDTO.builder()
                .name("OGC Nice").acronym("OGCN").budget(BigDecimal.valueOf(1000)).build();
        when(teamRepository.existsByName("OGC Nice")).thenReturn(false);
        when(teamRepository.existsByAcronym("OGCN")).thenReturn(true);

        assertThatThrownBy(() -> teamService.createTeam(dto))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Acronyme déjà utilisé");
    }
}

