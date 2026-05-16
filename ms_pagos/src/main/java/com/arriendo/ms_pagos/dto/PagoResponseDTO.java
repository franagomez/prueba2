package com.arriendo.ms_pagos.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoResponseDTO {

    private Long id;
    private Double monto;
    private String metodoPago;
    private Boolean pagado;
    private LocalDate fechaPago;
    private String descripcion;
}
