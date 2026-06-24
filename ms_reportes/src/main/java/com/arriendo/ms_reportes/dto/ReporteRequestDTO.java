package com.arriendo.ms_reportes.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO utilizado para registrar o actualizar reportes")
public class ReporteRequestDTO {

    @Schema(description = "Tipo de reporte generado", example = "Mensual")
    @NotBlank(message = "El tipo de reporte es obligatorio")
    private String tipoReporte;

    @Schema(description = "Descripción del reporte", example = "Reporte de ingresos del mes de junio")
    private String descripcion;

    @Schema(description = "Total de ingresos registrados", example = "2500000")
    @NotNull(message = "El total de ingresos es obligatorio")
    @PositiveOrZero(message = "El total no puede ser negativo")
    private Double totalIngresos;

    @Schema(description = "Cantidad total de reservas registradas", example = "45")
    @NotNull(message = "El total de reservas es obligatorio")
    @PositiveOrZero(message = "Las reservas no pueden ser negativas")
    private Integer totalReservas;

    @Schema(description = "Fecha de generación del reporte", example = "2026-06-21")
    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fechaGeneracion;
}
