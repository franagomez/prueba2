package com.arriendo.ms_pagos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO utilizado para registrar o actualizar pagos")
public class PagoRequestDTO {

    @Schema(description = "Monto del pago", example = "85000")
    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser positivo")
    private Double monto;

    @Schema(description = "Método de pago utilizado", example = "Tarjeta de crédito")
    @NotBlank(message = "El método de pago es obligatorio")
    private String metodoPago;

    @Schema(description = "Indica si el pago fue realizado", example = "true")
    private Boolean pagado;

    @Schema(description = "Fecha del pago", example = "2026-06-20")
    @NotNull(message = "La fecha de pago es obligatoria")
    @PastOrPresent(message = "La fecha no puede ser futura")
    private LocalDate fechaPago;

    @Schema(description = "Descripción del pago", example = "Pago de reserva de vehículo")
    private String descripcion;
}
