package com.msvehiculos.ms_vehiculos.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO de respuesta para la información de vehículos")
public class VehiculoDTO {

    @Schema(description = "ID del vehículo", example = "1")
    private Integer id;
    @Schema(description = "Patente del vehículo", example = "ABCD12")
    private String patente;
    @Schema(description = "Marca del vehículo", example = "Toyota")
    private String marca;
    @Schema(description = "Modelo del vehículo", example = "Corolla")
    private String modelo;
    @Schema(description = "Año del vehículo", example = "2024")
    private Integer anio;
    @Schema(description = "Precio diario de arriendo", example = "35000")
    private Double precioArriendoDiario;
    @Schema(description = "Indica si el vehículo está disponible", example = "true")
    private boolean disponible;
    @Schema(description = "Fecha de registro del vehículo", example = "2026-06-20")
    private LocalDate fechaRegistro;


    //categoriaId nos permite saber a que categoria pertenece el vehiculo
    // sin necesidad de devolver el objeto completo
    @Schema(description = "ID de la categoría asociada", example = "2")
    private Integer categoriaId;


}
