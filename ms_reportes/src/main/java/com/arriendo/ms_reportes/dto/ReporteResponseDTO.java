package com.arriendo.ms_reportes.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO utilizado para responder información de reportes")
public class ReporteResponseDTO {

    @Schema(description = "Identificador único del reporte", example = "1")
    private Long id;
    @Schema(description = "Tipo de reporte generado", example = "Mensual")
    private String tipoReporte;
    @Schema(description = "Descripción del reporte", example = "Reporte de ingresos del mes de junio")
    private String descripcion;
    @Schema(description = "Total de ingresos registrados", example = "2500000")
    private Double totalIngresos;
    @Schema(description = "Cantidad total de reservas registradas", example = "45")
    private Integer totalReservas;
    @Schema(description = "Fecha de generación del reporte", example = "2026-06-21")
    private LocalDate fechaGeneracion;
}
