package com.msreservas.ms_reservas.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "reserva")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nombreCliente;

    @Column(nullable = false)
    private Integer vehiculoId;

    @Column(nullable = false)
    private Integer cantidadDias;

    @Column(nullable = false)
    private Double totalReserva;

    @Column(nullable = false)
    private boolean activa = true;

    @Column(nullable = false)
    private LocalDate fechaReserva;

    @ManyToOne
    @JoinColumn(name = "estado_reserva_id")
    private EstadoReserva estadoReserva;

}
