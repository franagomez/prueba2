package com.msreservas.ms_reservas.Runner;

import com.msreservas.ms_reservas.Model.EstadoReserva;
import com.msreservas.ms_reservas.Model.Reserva;
import com.msreservas.ms_reservas.Repository.EstadoReservaRepository;
import com.msreservas.ms_reservas.Repository.ReservaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
public class DataLoader implements CommandLineRunner {

    private final ReservaRepository reservaRepository;
    private final EstadoReservaRepository estadoReservaRepository;

    public DataLoader(ReservaRepository reservaRepository, EstadoReservaRepository estadoReservaRepository) {
        this.reservaRepository = reservaRepository;
        this.estadoReservaRepository = estadoReservaRepository;
    }

    @Override
    public void run(String... args) {
        if (estadoReservaRepository.count() > 0) {
            log.info("Ya existen estados de reserva cargados, se omite la carga inicial de datos");
            return;
        }

        log.info("Cargando datos iniciales de estados de reserva y reservas");

        EstadoReserva confirmada = crearEstado("CONFIRMADA", "Reserva confirmada", 1);
        EstadoReserva pendiente = crearEstado("PENDIENTE", "Reserva pendiente de confirmación", 2);
        EstadoReserva enCurso = crearEstado("EN_CURSO", "Reserva actualmente en curso", 3);
        EstadoReserva finalizada = crearEstado("FINALIZADA", "Reserva finalizada correctamente", 4);
        EstadoReserva cancelada = crearEstado("CANCELADA", "Reserva cancelada por el cliente", 5);

        estadoReservaRepository.saveAll(List.of(confirmada, pendiente, enCurso, finalizada, cancelada));
        log.info("Se cargaron {} estados de reserva de prueba", estadoReservaRepository.count());

        reservaRepository.saveAll(List.of(
                crearReserva(1, "Ghislaine", 1, 7, 350000.0, true, confirmada),
                crearReserva(2, "Francisca", 2, 3, 120000.0, true, pendiente),
                crearReserva(3, "Marco", 3, 10, 500000.0, true, enCurso),
                crearReserva(4, "Pedro", 1, 5, 200000.0, false, finalizada),
                crearReserva(5, "Valentina", 4, 2, 80000.0, false, cancelada)
        ));
        log.info("Se cargaron {} reservas de prueba", reservaRepository.count());
    }

    private EstadoReserva crearEstado(String nombre, String descripcion, int prioridad) {
        EstadoReserva estado = new EstadoReserva();
        estado.setNombre(nombre);
        estado.setDescripcion(descripcion);
        estado.setPrioridad(prioridad);
        estado.setActivo(true);
        estado.setFechaCreacion(LocalDate.now());
        return estado;
    }

    private Reserva crearReserva(Integer clienteId, String nombreCliente, Integer vehiculoId,
                                  Integer cantidadDias, Double totalReserva, boolean activa,
                                  EstadoReserva estado) {
        Reserva reserva = new Reserva();
        reserva.setClienteId(clienteId);
        reserva.setNombreCliente(nombreCliente);
        reserva.setVehiculoId(vehiculoId);
        reserva.setCantidadDias(cantidadDias);
        reserva.setTotalReserva(totalReserva);
        reserva.setActiva(activa);
        reserva.setFechaReserva(LocalDate.now());
        reserva.setEstadoReserva(estado);
        return reserva;
    }
}