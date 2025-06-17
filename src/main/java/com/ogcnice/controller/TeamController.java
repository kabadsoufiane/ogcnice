package com.ogcnice.controller;

import com.ogcnice.dto.*;
import com.ogcnice.service.TeamService;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
@Slf4j
@Validated
@Tag(name = "Teams", description = "API de gestion des équipes de football")
public class TeamController {
    private final TeamService teamService;

    @Operation(
            summary = "Lister les équipes",
            description = "Retourne la liste paginée et triée des équipes avec leurs joueurs"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès")
    })
    @GetMapping
    public ResponseEntity<Page<TeamDTO>> getAllTeams(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(teamService.findAllTeams(pageable));
    }

    @Operation(
            summary = "Créer une équipe",
            description = "Crée une nouvelle équipe avec ou sans joueurs associés"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Équipe créée avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "409", description = "Équipe déjà existante")
    })
    @PostMapping
    public ResponseEntity<TeamDTO> createTeam(@Valid @RequestBody TeamCreateDTO dto) {
        TeamDTO created = teamService.createTeam(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
