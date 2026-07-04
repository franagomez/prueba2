package com.arriendo.ms_pagos.config;

import com.arriendo.ms_pagos.model.Pago;
import com.arriendo.ms_pagos.repository.PagoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final PagoRepository pagoRepository;

    public DataLoader(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }

    @Override
    public void run(String... args) {

        if (pagoRepository.count() == 0) {

            pagoRepository.save(new Pago(
                    null,
                    150000.0,
                    "Transferencia",
                    true,
                    LocalDate.of(2025, 1, 1),
                    "Pago inicial de reserva"
            ));

            pagoRepository.save(new Pago(
                    null,
                    200000.0,
                    "Tarjeta",
                    true,
                    LocalDate.of(2025, 2, 10),
                    "Pago arriendo vehículo"
            ));

            pagoRepository.save(new Pago(
                    null,
                    95000.0,
                    "Efectivo",
                    false,
                    LocalDate.of(2025, 3, 5),
                    "Pago pendiente"
            ));

            pagoRepository.save(new Pago(
                    null,
                    320000.0,
                    "Transferencia",
                    true,
                    LocalDate.of(2025, 4, 12),
                    "Pago arriendo camioneta"
            ));

            pagoRepository.save(new Pago(
                    null,
                    75000.0,
                    "Tarjeta",
                    false,
                    LocalDate.of(2025, 5, 20),
                    "Pago pendiente de garantía"
            ));
        }
    }
}
