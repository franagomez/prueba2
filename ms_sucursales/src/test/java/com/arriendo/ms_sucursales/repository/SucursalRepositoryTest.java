package com.arriendo.ms_sucursales.repository;

import com.arriendo.ms_sucursales.model.Region;
import com.arriendo.ms_sucursales.model.Sucursal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class SucursalRepositoryTest {

    @Autowired
    private SucursalRepository sucursalRepository;

    @Autowired
    private RegionRepository regionRepository;

    private Region region;

    @BeforeEach
    void setUp() {
        region = regionRepository.save(new Region(
                null,
                "Metropolitana",
                "RM",
                13,
                true,
                LocalDate.of(2025, 1, 1)
        ));
    }

    @Test
    void guardar_DebePersistirYAsignarId() {
        Sucursal sucursal = new Sucursal(
                null,
                "Sucursal Santiago Centro",
                "Av. Alameda 123",
                50,
                true,
                LocalDate.of(2020, 5, 10),
                region
        );

        Sucursal guardada = sucursalRepository.save(sucursal);

        assertNotNull(guardada.getId());
        assertEquals("Sucursal Santiago Centro", guardada.getNombre());
    }

    @Test
    void buscarPorId_CuandoExiste_DebeRetornarSucursal() {
        Sucursal sucursal = sucursalRepository.save(new Sucursal(
                null,
                "Sucursal Viña del Mar",
                "Av. Libertad 456",
                30,
                true,
                LocalDate.of(2021, 8, 15),
                region
        ));

        Optional<Sucursal> resultado = sucursalRepository.findById(sucursal.getId());

        assertTrue(resultado.isPresent());
        assertEquals("Sucursal Viña del Mar", resultado.get().getNombre());
    }

    @Test
    void buscarSucursalesOperativas_DebeRetornarSoloOperativas() {
        sucursalRepository.save(new Sucursal(
                null, "Sucursal Operativa", "Calle 1", 20, true,
                LocalDate.of(2021, 1, 1), region
        ));
        sucursalRepository.save(new Sucursal(
                null, "Sucursal Cerrada", "Calle 2", 10, false,
                LocalDate.of(2019, 1, 1), region
        ));

        List<Sucursal> operativas = sucursalRepository.buscarSucursalesOperativas();

        assertEquals(1, operativas.size());
        assertTrue(operativas.get(0).getOperativa());
        assertEquals("Sucursal Operativa", operativas.get(0).getNombre());
    }
}
