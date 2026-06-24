package com.arriendo.ms_sucursales.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO de respuesta para regiones")
public class RegionResponseDTO {

    @Schema(description = "ID de la región", example = "1")
    private Integer id;
    @Schema(description = "Nombre de la región", example = "Región Metropolitana")
    private String nombre;
    @Schema(description = "Código de la región", example = "RM")
    private String codigo;
    @Schema(description = "Número de la región", example = "13")
    private Integer numeroRegion;
    @Schema(description = "Indica si la región está activa", example = "true")
    private Boolean activo;
    @Schema(description = "Fecha de creación", example = "2026-06-20")
    private LocalDate fechaCreacion;
}
