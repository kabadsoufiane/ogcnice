package com.ogcnice.dto;

import lombok.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamUpdateDTO {
    @NotNull(message = "Le budget est obligatoire")
    @DecimalMin(value = "0.0", inclusive = false, message = "Le budget doit être positif")
    private BigDecimal budget;

    @Valid
    @Builder.Default
    private List<PlayerCreateDTO> players = new ArrayList<>();
}