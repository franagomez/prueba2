package com.arriendo.ms_pagos.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "pagos")

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double monto;

    private String metodoPago;

    private Boolean pagado;

    private LocalDate fechaPago;

    private String descripcion;
}
