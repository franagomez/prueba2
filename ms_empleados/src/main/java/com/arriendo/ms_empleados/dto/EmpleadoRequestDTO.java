package com.arriendo.ms_empleados.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO utilizado para registrar o actualizar empleados")
public class EmpleadoRequestDTO {

    @Schema(description = "Nombre del empleado", example = "Juan")
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Schema(description = "Apellido del empleado", example = "Pérez")
    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @Schema(description = "Correo electrónico del empleado", example = "juan.perez@empresa.cl")
    @Email(message = "Debe ingresar un email válido")
    @NotBlank(message = "El email es obligatorio")
    private String email;

    @Schema(description = "Sueldo del empleado", example = "850000")
    @NotNull(message = "El sueldo es obligatorio")
    @Positive(message = "El sueldo debe ser positivo")
    private Double sueldo;

    @Schema(description = "Indica si el empleado se encuentra activo", example = "true")
    private Boolean activo;

    @Schema(description = "Cargo que desempeña el empleado", example = "Supervisor")
    @NotBlank(message = "El cargo es obligatorio")
    private String cargo;

    @Schema(description = "Fecha de contratación del empleado", example = "2024-03-15")
    @NotNull(message = "La fecha de contratación es obligatoria")
    @PastOrPresent(message = "La fecha no puede ser futura")
    private LocalDate fechaContratacion;
}
