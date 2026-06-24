package com.msreservas.ms_reservas.DTO;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO de respuesta para reservas")
public class ReservaDTO {

    @Schema(description = "ID de la reserva", example = "1")
    private Integer id;
    @Schema(description = "ID del cliente asociado", example = "1")
    private Integer clienteId;
    @Schema(description = "Nombre del cliente", example = "Joel Araya")
    private String nombreCliente;
    @Schema(description = "ID del vehículo asociado", example = "2")
    private Integer vehiculoId;
    @Schema(description = "Cantidad de días de la reserva", example = "5")
    private Integer cantidadDias;
    @Schema(description = "Monto total de la reserva", example = "175000")
    private Double totalReserva;
    @Schema(description = "Indica si la reserva está activa", example = "true")
    private boolean activa;
    @Schema(description = "Fecha de la reserva", example = "2026-06-20")
    private LocalDate fechaReserva;
    @Schema(description = "ID del estado de reserva asociado", example = "1")
    private Integer estadoReservaId;

}
