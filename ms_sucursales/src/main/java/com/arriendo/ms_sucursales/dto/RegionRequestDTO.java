package com.arriendo.ms_sucursales.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO utilizado para registrar o actualizar regiones")
public class RegionRequestDTO {

    @Schema(description = "Nombre de la región", example = "Región Metropolitana")
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100)
    private String nombre;

    @Schema(description = "Código de la región", example = "RM")
    @NotBlank(message = "El código es obligatorio")
    @Size(min = 2, max = 20)
    private String codigo;

    @Schema(description = "Número de la región", example = "13")
    @NotNull(message = "El número de región es obligatorio")
    @Positive(message = "El número de región debe ser positivo")
    private Integer numeroRegion;

    @Schema(description = "Indica si la región está activa", example = "true")
    private Boolean activo;

    @Schema(description = "Fecha de creación", example = "2026-06-20")
    @NotNull(message = "La fecha de creación es obligatoria")
    @PastOrPresent(message = "La fecha no puede ser futura")
    private LocalDate fechaCreacion;
}
