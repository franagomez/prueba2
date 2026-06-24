package com.msvehiculos.ms_vehiculos.DTO;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO utilizado para registrar o actualizar vehículos")
public class VehiculoRequestDTO {

    @Schema(description = "Patente del vehículo", example = "ABCD12")
    @NotBlank
    @Size(min = 5, max = 10)
    private String patente;

    @Schema(description = "Marca del vehículo", example = "Toyota")
    @NotBlank
    @Size(min = 2, max = 50)
    private String marca;

    @Schema(description = "Modelo del vehículo", example = "Corolla")
    @NotBlank
    @Size(min = 2, max = 50)
    private String modelo;

    @Schema(description = "Año del vehículo", example = "2024")
    @NotNull
    @Positive
    private Integer anio;

    @Schema(description = "Precio diario de arriendo", example = "35000")
    @NotNull
    @Positive
    private Double precioArriendoDiario;

    @Schema(description = "Disponibilidad del vehículo", example = "true")
    private boolean disponible = true;

    @Schema(description = "Fecha de registro", example = "2026-06-20")
    @NotNull
    @PastOrPresent
    private LocalDate fechaRegistro;

    @Schema(description = "ID de la categoría asociada", example = "2")
    @NotNull
    private Integer categoriaId;
}
