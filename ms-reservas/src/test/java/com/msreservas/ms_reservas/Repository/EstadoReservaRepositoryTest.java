package com.msreservas.ms_reservas.Repository;

import com.msreservas.ms_reservas.Model.EstadoReserva;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class EstadoReservaRepositoryTest {

    @Autowired
    private EstadoReservaRepository estadoReservaRepository;

    @Test
    void guardar_DebePersistirYAsignarId() {
        EstadoReserva estado = new EstadoReserva(
                null,
                "Confirmada",
                "Reserva confirmada por el sistema",
                1,
                true,
                LocalDate.of(2025, 1, 1),
                null
        );

        EstadoReserva guardado = estadoReservaRepository.save(estado);

        assertNotNull(guardado.getId());
        assertEquals("Confirmada", guardado.getNombre());
    }

    @Test
    void buscarPorId_CuandoExiste_DebeRetornarEstado() {
        EstadoReserva estado = estadoReservaRepository.save(new EstadoReserva(
                null,
                "Pendiente",
                "Reserva pendiente de confirmación",
                2,
                true,
                LocalDate.of(2025, 1, 1),
                null
        ));

        Optional<EstadoReserva> resultado = estadoReservaRepository.findById(estado.getId());

        assertTrue(resultado.isPresent());
        assertEquals("Pendiente", resultado.get().getNombre());
    }
}
