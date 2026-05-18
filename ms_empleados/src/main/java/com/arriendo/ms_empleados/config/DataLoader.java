package com.arriendo.ms_empleados.config;

import com.arriendo.ms_empleados.model.Empleado;
import com.arriendo.ms_empleados.repository.EmpleadoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final EmpleadoRepository empleadoRepository;

    public DataLoader(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @Override
    public void run(String... args) {

        if (empleadoRepository.count() == 0) {

            empleadoRepository.save(new Empleado(
                    null,
                    "Francisca",
                    "Gomez",
                    "francisca.gomez@empresa.cl",
                    850000.0,
                    true,
                    "Soporte TI",
                    LocalDate.of(2025, 1, 10)
            ));

            empleadoRepository.save(new Empleado(
                    null,
                    "Carlos",
                    "Muñoz",
                    "carlos.munoz@empresa.cl",
                    920000.0,
                    true,
                    "Analista de Sistemas",
                    LocalDate.of(2024, 3, 15)
            ));

            empleadoRepository.save(new Empleado(
                    null,
                    "Valentina",
                    "Rojas",
                    "valentina.rojas@empresa.cl",
                    780000.0,
                    false,
                    "Asistente Administrativo",
                    LocalDate.of(2023, 8, 20)
            ));
        }
    }
}
