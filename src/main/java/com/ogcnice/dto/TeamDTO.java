package com.ogcnice.dto;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Représentation d'une équipe avec ses joueurs")
public class TeamDTO {
    private Long id;
    private String name;
    private String acronym;
    private BigDecimal budget;
    private List<PlayerDTO> players;
}
