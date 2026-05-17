package com.msvehiculos.ms_vehiculos.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "categoria")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 200)
    private String descripcion;

    @Column(nullable = false)
    private Integer cantidadVehiculos;

    @Column(nullable = false)
    private boolean activa = true;

    @Column(nullable = false)
    private LocalDate fechaCreacion;

    // relacion de uno a muchos con vehiculo
    //tiene impacto directo en vehiculo al estar relacionadas
    // Lista los vehiculos de una misma categoria
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
    private List<Vehiculo> vehiculos;

}
