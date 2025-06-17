package com.ogcnice.dto;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Données pour créer une nouvelle équipe")
public class TeamCreateDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String acronym;
    @NotNull
    private BigDecimal budget;
    @Valid
    private List<PlayerCreateDTO> players;
}