package com.arriendo.ms_sucursales.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "sucursal")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sucursal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 150)
    private String direccion;

    @Column(nullable = false)
    private Integer capacidadVehiculos;

    @Column(nullable = false)
    private Boolean operativa;

    @Column(nullable = false)
    private LocalDate fechaApertura;

    @ManyToOne
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;
}