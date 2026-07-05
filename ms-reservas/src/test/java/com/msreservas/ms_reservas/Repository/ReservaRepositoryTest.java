package com.msreservas.ms_reservas.Repository;

import com.msreservas.ms_reservas.Model.EstadoReserva;
import com.msreservas.ms_reservas.Model.Reserva;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class ReservaRepositoryTest {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private EstadoReservaRepository estadoReservaRepository;

    private EstadoReserva estado;

    @BeforeEach
    void setUp() {
        estado = estadoReservaRepository.save(new EstadoReserva(
                null,
                "Confirmada",
                "Reserva confirmada por el sistema",
                1,
                true,
                LocalDate.of(2025, 1, 1),
                null
        ));
    }

    @Test
    void guardar_DebePersistirYAsignarId() {
        Reserva reserva = new Reserva(
                null,
                1,
                "Joel Araya",
                2,
                5,
                175000.0,
                true,
                LocalDate.of(2026, 6, 20),
                estado
        );

        Reserva guardada = reservaRepository.save(reserva);

        assertNotNull(guardada.getId());
        assertEquals("Joel Araya", guardada.getNombreCliente());
    }

    @Test
    void buscarPorId_CuandoExiste_DebeRetornarReserva() {
        Reserva reserva = reservaRepository.save(new Reserva(
                null,
                2,
                "Francisca Gomez",
                3,
                3,
                120000.0,
                true,
                LocalDate.of(2026, 6, 21),
                estado
        ));

        Optional<Reserva> resultado = reservaRepository.findById(reserva.getId());

        assertTrue(resultado.isPresent());
        assertEquals("Francisca Gomez", resultado.get().getNombreCliente());
    }
}
