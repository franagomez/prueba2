package com.msreservas.ms_reservas.DTO;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO utilizado para registrar o actualizar reservas")
public class ReservaRequestDTO {

    @Schema(description = "ID del cliente asociado", example = "1")
    @NotNull
    @Positive
    private Integer clienteId;

    @Schema(description = "Nombre del cliente", example = "Joel Araya")
    @NotBlank
    @Size(min = 3, max = 100)
    private String nombreCliente;

    @Schema(description = "ID del vehículo asociado", example = "2")
    @NotNull
    @Positive
    private Integer vehiculoId;

    @Schema(description = "Cantidad de días de la reserva", example = "5")
    @NotNull
    @Positive
    private Integer cantidadDias;

    @Schema(description = "Monto total de la reserva", example = "175000")
    @NotNull
    @Positive
    private Double totalReserva;

    @Schema(description = "Indica si la reserva está activa", example = "true")
    private boolean activa = true;

    @Schema(description = "Fecha de la reserva", example = "2026-06-20")
    @NotNull
    @PastOrPresent
    private LocalDate fechaReserva;

    @Schema(description = "ID del estado de reserva asociado", example = "1")
    @NotNull
    private Integer estadoReservaId;
}
