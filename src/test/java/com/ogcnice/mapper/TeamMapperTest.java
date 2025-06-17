package com.ogcnice.mapper;

import com.ogcnice.dto.TeamDTO;
import com.ogcnice.entity.Team;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TeamMapperTest {

    @Autowired
    private TeamMapper teamMapper;

    @Test
    void toDto_shouldMapFieldsCorrectly() {
        Team team = Team.builder()
                .id(1L)
                .name("OGC Nice")
                .acronym("OGCN")
                .budget(BigDecimal.valueOf(50000000))
                .players(new ArrayList<>())
                .build();

        TeamDTO dto = teamMapper.toDto(team);

        assertThat(dto.getName()).isEqualTo("OGC Nice");
        assertThat(dto.getAcronym()).isEqualTo("OGCN");
        assertThat(dto.getBudget()).isEqualTo(BigDecimal.valueOf(50000000));
    }
}
