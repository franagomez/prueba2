package com.arriendo.ms_empleados.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "empleados")

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String apellido;

    @Column(unique = true)
    private String email;

    private Double sueldo;

    private Boolean activo;

    private String cargo;

    private LocalDate fechaContratacion;
}