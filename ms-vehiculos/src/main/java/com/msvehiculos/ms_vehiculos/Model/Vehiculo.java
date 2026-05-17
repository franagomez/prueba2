package com.msvehiculos.ms_vehiculos.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "vehiculo")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String patente;

    @Column(nullable = false, length = 50)
    private String marca;

    @Column(nullable = false, length = 50)
    private String modelo;

    @Column(nullable = false)
    private Integer anio;

    @Column(nullable = false)
    private Double precioArriendoDiario;

    @Column(nullable = false)
    private boolean disponible = true;

    @Column(nullable = false)
    private LocalDate fechaRegistro;

    @ManyToOne
    @JoinColumn(name ="categoriaId")
    private Categoria categoria;


}
