package com.ogcnice.dto;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.ogcnice.entity.Player;
import jakarta.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Données pour créer un nouveau joueur")
public class PlayerCreateDTO {
    @NotBlank
    private String name;
    @NotNull
    private Player.Position position;
}