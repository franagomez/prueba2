package com.msreservas.ms_reservas.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "estado_reserva")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoReserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 200)
    private String descripcion;

    @Column(nullable = false)
    private Integer prioridad;

    @Column(nullable = false)
    private boolean activo = true;

    @Column(nullable = false)
    private LocalDate fechaCreacion;

    @OneToMany(mappedBy = "estadoReserva", cascade = CascadeType.ALL)
    private List<Reserva> reservas;
}
