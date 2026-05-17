package com.arriendo.ms_reportes.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteResponseDTO {

    private Long id;
    private String tipoReporte;
    private String descripcion;
    private Double totalIngresos;
    private Integer totalReservas;
    private LocalDate fechaGeneracion;
}
