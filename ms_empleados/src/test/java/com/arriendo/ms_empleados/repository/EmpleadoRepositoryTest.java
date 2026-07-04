package com.arriendo.ms_empleados.repository;

import com.arriendo.ms_empleados.model.Empleado;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class EmpleadoRepositoryTest {

    @org.springframework.beans.factory.annotation.Autowired
    private EmpleadoRepository empleadoRepository;

    @Test
    void guardarYRecuperar_DebePersistirEmpleado() {
        Empleado empleado = new Empleado(
                null,
                "Francisca",
                "Gomez",
                "francisca.gomez@test.cl",
                850000.0,
                true,
                "Soporte TI",
                LocalDate.of(2024, 1, 10)
        );

        Empleado guardado = empleadoRepository.save(empleado);

        assertNotNull(guardado.getId());

        Optional<Empleado> recuperado = empleadoRepository.findById(guardado.getId());

        assertTrue(recuperado.isPresent());
        assertEquals("Francisca", recuperado.get().getNombre());
        assertEquals("Gomez", recuperado.get().getApellido());
        assertEquals("francisca.gomez@test.cl", recuperado.get().getEmail());
    }

    @Test
    void buscarActivosPorAnio_CuandoExistenActivosEnEseAnio_DebeRetornarlos() {
        empleadoRepository.save(new Empleado(
                null,
                "Carlos",
                "Muñoz",
                "carlos.munoz@test.cl",
                920000.0,
                true,
                "Analista",
                LocalDate.of(2024, 3, 15)
        ));

        empleadoRepository.save(new Empleado(
                null,
                "Valentina",
                "Rojas",
                "valentina.rojas@test.cl",
                780000.0,
                false,
                "Asistente",
                LocalDate.of(2024, 8, 20)
        ));

        empleadoRepository.save(new Empleado(
                null,
                "Matias",
                "Fernandez",
                "matias.fernandez@test.cl",
                1050000.0,
                true,
                "Jefe de Sucursal",
                LocalDate.of(2022, 5, 2)
        ));

        List<Empleado> activos2024 = empleadoRepository.buscarActivosPorAnio(2024);

        assertEquals(1, activos2024.size());
        assertEquals("Carlos", activos2024.get(0).getNombre());
        assertTrue(activos2024.get(0).getActivo());
    }

    @Test
    void buscarActivosPorAnio_CuandoNoHayCoincidencias_DebeRetornarListaVacia() {
        empleadoRepository.save(new Empleado(
                null,
                "Diego",
                "Vergara",
                "diego.vergara@test.cl",
                980000.0,
                false,
                "Mecánico",
                LocalDate.of(2021, 7, 30)
        ));

        List<Empleado> activos2030 = empleadoRepository.buscarActivosPorAnio(2030);

        assertNotNull(activos2030);
        assertTrue(activos2030.isEmpty());
    }
}
