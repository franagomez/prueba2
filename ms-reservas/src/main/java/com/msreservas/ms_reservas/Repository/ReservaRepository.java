package com.msreservas.ms_reservas.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.msreservas.ms_reservas.Model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva,Integer> {

    // Query method buscar reservas activas
    // con cantidad de dias mayor al indicado

    @Query("""
    SELECT r
    FROM Reserva r
    WHERE r.fechaReserva >= :fecha
    ORDER BY r.fechaReserva DESC
    """)
    List<Reserva> buscarReservasDesdeFecha(@Param("fecha") LocalDate fecha);

    List<Reserva> findByActivaTrueAndCantidadDiasGreaterThan(Integer dias);
}
