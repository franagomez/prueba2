package com.arriendo.ms_sucursales.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO de respuesta para sucursales")
public class SucursalResponseDTO {

    @Schema(description = "ID de la sucursal", example = "1")
    private Integer id;
    @Schema(description = "Nombre de la sucursal", example = "Sucursal Santiago Centro")
    private String nombre;
    @Schema(description = "Dirección de la sucursal", example = "Av. Providencia 1234")
    private String direccion;
    @Schema(description = "Capacidad máxima de vehículos", example = "50")
    private Integer capacidadVehiculos;
    @Schema(description = "Indica si la sucursal está operativa", example = "true")
    private Boolean operativa;
    @Schema(description = "Fecha de apertura", example = "2026-06-20")
    private LocalDate fechaApertura;
    @Schema(description = "ID de la región asociada", example = "1")
    private Integer regionId;
    @Schema(description = "Nombre de la región asociada", example = "Región Metropolitana")
    private String nombreRegion;
}
