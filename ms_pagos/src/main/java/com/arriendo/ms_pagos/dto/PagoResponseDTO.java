package com.arriendo.ms_pagos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO de respuesta para pagos")
public class PagoResponseDTO {

    @Schema(description = "ID del pago", example = "1")
    private Long id;
    @Schema(description = "Monto del pago", example = "85000")
    private Double monto;
    @Schema(description = "Método de pago utilizado", example = "Tarjeta de crédito")
    private String metodoPago;
    @Schema(description = "Indica si el pago fue realizado", example = "true")
    private Boolean pagado;
    @Schema(description = "Fecha del pago", example = "2026-06-20")
    private LocalDate fechaPago;
    @Schema(description = "Descripción del pago", example = "Pago de reserva de vehículo")
    private String descripcion;
}
