package com.msreservas.ms_reservas.Repository;

import com.msreservas.ms_reservas.Model.EstadoReserva;
import com.msreservas.ms_reservas.Model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva,Integer> {

    // Query method buscar reservas activas
    // con cantidad de dias mayor al indicado

    List<Reserva> findByActivaTrueAndCantidadDiasGreaterThan(Integer dias);
}
