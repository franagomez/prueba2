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
@Schema(description = "DTO utilizado para registrar o actualizar categorías")
public class CategoriaRequestDTO {

    @Schema(description = "Nombre de la categoría", example = "SUV")
    @NotBlank
    @Size(min = 3, max= 50)
    private String nombre;

    @Schema(description = "Descripción de la categoría", example = "Vehículos deportivos utilitarios")
    @NotBlank
    @Size(min = 5, max= 50)
    private String descripcion;

    @Schema(description = "Cantidad de vehículos asociados", example = "15")
    @NotNull
    @PositiveOrZero
    private Integer cantidadVehiculos;

    @Schema(description = "Indica si la categoría está activa", example = "true")
    private boolean activa = true;

    @Schema(description = "Fecha de creación de la categoría", example = "2026-06-20")
    @NotNull
    @PastOrPresent
    private LocalDate fechaCreacion;

}
