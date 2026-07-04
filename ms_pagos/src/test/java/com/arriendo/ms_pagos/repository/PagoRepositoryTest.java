package com.arriendo.ms_pagos.repository;

import com.arriendo.ms_pagos.model.Pago;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class PagoRepositoryTest {

    @Autowired
    private PagoRepository pagoRepository;

    @Test
    void guardarYRecuperarPago_debeFuncionar() {
        Pago pago = new Pago(
                null,
                150000.0,
                "Transferencia",
                true,
                LocalDate.of(2025, 1, 1),
                "Pago inicial de reserva"
        );

        Pago guardado = pagoRepository.save(pago);

        Optional<Pago> recuperado = pagoRepository.findById(guardado.getId());

        assertTrue(recuperado.isPresent());
        assertEquals("Transferencia", recuperado.get().getMetodoPago());
        assertEquals(150000.0, recuperado.get().getMonto());
    }

    @Test
    void buscarPagosPorRango_debeRetornarPagosDentroDelRango() {
        pagoRepository.save(new Pago(
                null, 50000.0, "Efectivo", true, LocalDate.of(2025, 2, 1), "Pago bajo"));
        pagoRepository.save(new Pago(
                null, 150000.0, "Tarjeta", true, LocalDate.of(2025, 3, 1), "Pago medio"));
        pagoRepository.save(new Pago(
                null, 500000.0, "Transferencia", true, LocalDate.of(2025, 4, 1), "Pago alto"));

        List<Pago> resultado = pagoRepository.buscarPagosPorRango(100000.0, 200000.0);

        assertEquals(1, resultado.size());
        assertEquals("Pago medio", resultado.get(0).getDescripcion());
    }

    @Test
    void buscarPagosPorRango_debeOrdenarPorFechaDesc() {
        pagoRepository.save(new Pago(
                null, 100000.0, "Efectivo", true, LocalDate.of(2025, 1, 1), "Primero"));
        pagoRepository.save(new Pago(
                null, 120000.0, "Tarjeta", true, LocalDate.of(2025, 6, 1), "Segundo"));

        List<Pago> resultado = pagoRepository.buscarPagosPorRango(50000.0, 200000.0);

        assertEquals(2, resultado.size());
        assertEquals("Segundo", resultado.get(0).getDescripcion());
        assertEquals("Primero", resultado.get(1).getDescripcion());
    }
}
