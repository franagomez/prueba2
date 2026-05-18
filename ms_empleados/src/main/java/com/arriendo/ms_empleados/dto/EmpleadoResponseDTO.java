package com.arriendo.ms_empleados.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoResponseDTO {

    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private Double sueldo;
    private Boolean activo;
    private String cargo;
    private LocalDate fechaContratacion;
}
