package com.ogcnice.mapper;

import com.ogcnice.dto.TeamCreateDTO;
import com.ogcnice.dto.TeamDTO;
import com.ogcnice.dto.TeamUpdateDTO;
import com.ogcnice.entity.Player;
import com.ogcnice.entity.Team;
import org.mapstruct.*;

import java.util.List;


@Mapper(componentModel = "spring", uses = {PlayerMapper.class})
public interface TeamMapper {
    TeamDTO toDto(Team entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "players", ignore = true)
    Team toEntity(TeamCreateDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "players", ignore = true)
    Team toEntity(TeamUpdateDTO dto);

    List<TeamDTO> toDtoList(List<Team> entities);
}