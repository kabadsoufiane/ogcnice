package com.ogcnice.dto;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.ogcnice.entity.Player;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Représentation d'un joueur")
public class PlayerDTO {
    private Long id;
    private String name;
    private Player.Position position;
}

