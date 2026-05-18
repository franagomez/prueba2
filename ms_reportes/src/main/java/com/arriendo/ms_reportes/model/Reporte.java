package com.arriendo.ms_reportes.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "reportes")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipoReporte;

    private String descripcion;

    private Double totalIngresos;

    private Integer totalReservas;

    private LocalDate fechaGeneracion;
}