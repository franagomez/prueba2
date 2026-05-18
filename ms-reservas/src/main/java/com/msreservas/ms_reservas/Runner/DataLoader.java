package com.msreservas.ms_reservas.Runner;


import com.msreservas.ms_reservas.Model.EstadoReserva;
import com.msreservas.ms_reservas.Model.Reserva;
import com.msreservas.ms_reservas.Repository.EstadoReservaRepository;
import com.msreservas.ms_reservas.Repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private EstadoReservaRepository estadoReservaRepository;

    @Override
    public void run(String... args) throws Exception {

        if(estadoReservaRepository.count() == 0){

            EstadoReserva estado = new EstadoReserva();

            estado.setNombre("CONFIRMADA");
            estado.setDescripcion("Reserva confirmada");
            estado.setPrioridad(1);
            estado.setActivo(true);
            estado.setFechaCreacion(LocalDate.now());

            estadoReservaRepository.save(estado);

            Reserva reserva = new Reserva();

            reserva.setNombreCliente("Ghislaine");
            reserva.setVehiculoId(1);
            reserva.setCantidadDias(7);
            reserva.setTotalReserva(350000.0);
            reserva.setActiva(true);
            reserva.setFechaReserva(LocalDate.now());
            reserva.setEstadoReserva(estado);

            reservaRepository.save(reserva);

            System.out.println("Datos de prueba cargados");
        }




    }
}
