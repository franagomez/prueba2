package com.arriendo.ms_sucursales.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO utilizado para registrar o actualizar sucursales")
public class SucursalRequestDTO {

    @Schema(description = "Nombre de la sucursal", example = "Sucursal Santiago Centro")
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100)
    private String nombre;

    @Schema(description = "Dirección de la sucursal", example = "Av. Providencia 1234")
    @NotBlank(message = "La dirección es obligatoria")
    @Size(min = 5, max = 150)
    private String direccion;

    @Schema(description = "Capacidad máxima de vehículos", example = "50")
    @NotNull(message = "La capacidad es obligatoria")
    @Positive(message = "La capacidad debe ser positiva")
    private Integer capacidadVehiculos;

    @Schema(description = "Indica si la sucursal está operativa", example = "true")
    private Boolean operativa;

    @Schema(description = "Fecha de apertura", example = "2026-06-20")
    @NotNull(message = "La fecha de apertura es obligatoria")
    @PastOrPresent(message = "La fecha no puede ser futura")
    private LocalDate fechaApertura;

    @Schema(description = "ID de la región asociada", example = "1")
    @NotNull(message = "El id de la región es obligatorio")
    @Positive(message = "El id de la región debe ser positivo")
    private Integer regionId;
}