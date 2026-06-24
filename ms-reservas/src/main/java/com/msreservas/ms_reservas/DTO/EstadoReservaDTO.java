package com.msreservas.ms_reservas.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO de respuesta para estados de reserva")
public class EstadoReservaDTO {

    @Schema(description = "ID del estado de reserva", example = "1")
    private Integer id;
    @Schema(description = "Nombre del estado", example = "Confirmada")
    private String nombre;
    @Schema(description = "Descripción del estado", example = "Reserva confirmada por el sistema")
    private String descripcion;
    @Schema(description = "Prioridad del estado", example = "1")
    private Integer prioridad;
    @Schema(description = "Indica si el estado está activo", example = "true")
    private boolean activo;
    @Schema(description = "Fecha de creación", example = "2026-06-20")
    private LocalDate fechaCreacion;
}
