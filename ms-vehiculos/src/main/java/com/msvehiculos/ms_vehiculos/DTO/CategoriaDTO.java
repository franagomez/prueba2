package com.msvehiculos.ms_vehiculos.DTO;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO de respuesta para categorías de vehículos")
public class CategoriaDTO {

    @Schema(description = "ID de la categoría", example = "1")
    private Integer id;
    @Schema(description = "Nombre de la categoría", example = "SUV")
    private String nombre;
    @Schema(description = "Descripción de la categoría", example = "Vehículos deportivos utilitarios")
    private String descripcion;
    @Schema(description = "Cantidad de vehículos asociados", example = "15")
    private Integer cantidadVehiculos;
    @Schema(description = "Indica si la categoría está activa", example = "true")
    private boolean activa = true;
    @Schema(description = "Fecha de creación de la categoría", example = "2026-06-20")
    private LocalDate fechaCreacion;

}
