package com.msclientes.ms_clientes.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class DireccionRequestDTO {

    @Schema(
            description = "Nombre de la calle",
            example = "Av. Providencia"
    )
    @NotBlank
    @Size(min = 2, max = 100)
    private String calle;

    @Schema(
            description = "Número de la dirección",
            example = "1234"
    )
    @NotBlank
    @Size(min = 1, max = 50)
    private String numero;

    @Schema(
            description = "Comuna de la dirección",
            example = "Providencia"
    )
    @NotBlank
    @Size(min = 2, max = 100)
    private String comuna;

    @Schema(
            description = "Ciudad de la dirección",
            example = "Santiago"
    )
    @NotBlank
    @Size(min = 2, max = 100)
    private String ciudad;

    @Schema(
            description = "Código postal",
            example = "7500000"
    )
    @NotNull
    @PositiveOrZero
    private Integer codigoPostal;

    @Schema(
            description = "Indica si corresponde a la dirección principal del cliente",
            example = "true"
    )
    private boolean principal = false;

    @Schema(
            description = "Fecha de registro de la dirección",
            example = "2026-03-01"
    )
    @NotNull
    @PastOrPresent
    private LocalDate fechaRegistro;

    @Schema(
            description = "Identificador del cliente asociado",
            example = "1"
    )
    @NotNull
    private Integer clienteId;

}
