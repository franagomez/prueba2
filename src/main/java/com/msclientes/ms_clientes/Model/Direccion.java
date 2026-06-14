package com.msclientes.ms_clientes.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "direccion")
public class Direccion {

    // PK DE DIRECCION
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String calle;

    // Numero de casa/departamento
    @Column(nullable = false, length = 50)
    private String numero;

    @Column(nullable = false, length = 100)
    private String comuna;

    @Column(nullable = false, length = 100)
    private String ciudad;

    @Column(nullable = false)
    private Integer codigoPostal;

    // para indicar si es la calle principal del cliente
    // true = principal
    // false = secundaria
    @Column(nullable = false)
    private boolean principal;

    @Column(nullable = false)
    private LocalDate fechaRegistro;

    // relacion muchos a uno entre direccion y cliente
    // FK joincolumn(name="cliente_id")
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}
