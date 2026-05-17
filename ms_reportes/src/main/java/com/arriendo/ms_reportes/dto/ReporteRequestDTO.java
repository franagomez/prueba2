package com.arriendo.ms_reportes.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteRequestDTO {

    @NotBlank(message = "El tipo de reporte es obligatorio")
    private String tipoReporte;

    private String descripcion;

    @NotNull(message = "El total de ingresos es obligatorio")
    @PositiveOrZero(message = "El total no puede ser negativo")
    private Double totalIngresos;

    @NotNull(message = "El total de reservas es obligatorio")
    @PositiveOrZero(message = "Las reservas no pueden ser negativas")
    private Integer totalReservas;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fechaGeneracion;
}
