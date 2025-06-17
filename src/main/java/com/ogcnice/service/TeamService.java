package com.ogcnice.service;

import com.ogcnice.dto.*;
import com.ogcnice.entity.Player;
import com.ogcnice.entity.Team;
import com.ogcnice.exception.BusinessException;
import com.ogcnice.mapper.TeamMapper;
import com.ogcnice.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TeamService {
    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;

    @Transactional(readOnly = true)
    public Page<TeamDTO> findAllTeams(Pageable pageable) {
        log.info("Récupération paginée et triée des équipes");
        return teamRepository.findAll(pageable).map(teamMapper::toDto);
    }

    public TeamDTO createTeam(TeamCreateDTO dto) {
        if (teamRepository.existsByName(dto.getName())) {
            throw new BusinessException("Nom d'équipe déjà utilisé");
        }
        if (teamRepository.existsByAcronym(dto.getAcronym())) {
            throw new BusinessException("Acronyme déjà utilisé");
        }
        Team team = teamMapper.toEntity(dto);
        if (dto.getPlayers() != null) {
            dto.getPlayers().forEach(playerDto -> {
                Player player = Player.builder()
                        .name(playerDto.getName())
                        .position(playerDto.getPosition())
                        .team(team)
                        .build();
                team.getPlayers().add(player);
            });
        }
        Team saved = teamRepository.save(team);
        log.info("Équipe créée : {}", saved.getName());
        return teamMapper.toDto(saved);
    }
}
