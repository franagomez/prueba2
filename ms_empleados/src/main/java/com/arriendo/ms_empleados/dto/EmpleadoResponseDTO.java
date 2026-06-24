package com.arriendo.ms_empleados.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO utilizado para responder información de empleados")
public class EmpleadoResponseDTO {

    @Schema(description = "Identificador único del empleado", example = "1")
    private Long id;
    @Schema(description = "Nombre del empleado", example = "Juan")
    private String nombre;
    @Schema(description = "Apellido del empleado", example = "Pérez")
    private String apellido;
    @Schema(description = "Correo electrónico del empleado", example = "juan.perez@empresa.cl")
    private String email;
    @Schema(description = "Sueldo del empleado", example = "850000")
    private Double sueldo;
    @Schema(description = "Estado del empleado", example = "true")
    private Boolean activo;
    @Schema(description = "Cargo del empleado", example = "Supervisor")
    private String cargo;
    @Schema(description = "Fecha de contratación", example = "2024-03-15")
    private LocalDate fechaContratacion;
}
