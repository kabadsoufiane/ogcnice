package com.ogcnice.mapper;

import com.ogcnice.dto.PlayerCreateDTO;
import com.ogcnice.dto.PlayerDTO;
import com.ogcnice.entity.Player;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlayerMapper {
    PlayerDTO toDto(Player entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "team", ignore = true)
    Player toEntity(PlayerCreateDTO dto);

    List<PlayerDTO> toDtoList(List<Player> entities);
}