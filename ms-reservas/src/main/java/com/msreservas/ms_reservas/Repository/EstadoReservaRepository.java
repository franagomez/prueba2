package com.msreservas.ms_reservas.Repository;


import com.msreservas.ms_reservas.Model.EstadoReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoReservaRepository extends JpaRepository<EstadoReserva,Integer> {
}
