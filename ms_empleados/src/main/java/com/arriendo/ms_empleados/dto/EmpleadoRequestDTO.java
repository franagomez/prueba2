package com.arriendo.ms_empleados.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @Email(message = "Debe ingresar un email válido")
    @NotBlank(message = "El email es obligatorio")
    private String email;

    @NotNull(message = "El sueldo es obligatorio")
    @Positive(message = "El sueldo debe ser positivo")
    private Double sueldo;

    private Boolean activo;

    @NotBlank(message = "El cargo es obligatorio")
    private String cargo;

    @NotNull(message = "La fecha de contratación es obligatoria")
    @PastOrPresent(message = "La fecha no puede ser futura")
    private LocalDate fechaContratacion;
}
