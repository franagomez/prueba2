package com.arriendo.ms_sucursales.repository;

import com.arriendo.ms_sucursales.model.Region;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class RegionRepositoryTest {

    @org.springframework.beans.factory.annotation.Autowired
    private RegionRepository regionRepository;

    @Test
    void guardar_DebePersistirYAsignarId() {
        Region region = new Region(
                null,
                "Metropolitana",
                "RM",
                13,
                true,
                LocalDate.of(2025, 1, 1)
        );

        Region guardada = regionRepository.save(region);

        assertNotNull(guardada.getId());
        assertEquals("Metropolitana", guardada.getNombre());
    }

    @Test
    void buscarPorId_CuandoExiste_DebeRetornarRegion() {
        Region region = regionRepository.save(new Region(
                null,
                "Valparaíso",
                "VAL",
                5,
                true,
                LocalDate.of(2025, 1, 1)
        ));

        Optional<Region> resultado = regionRepository.findById(region.getId());

        assertTrue(resultado.isPresent());
        assertEquals("VAL", resultado.get().getCodigo());
    }

    @Test
    void findAll_DebeRetornarTodasLasRegionesGuardadas() {
        regionRepository.save(new Region(
                null, "Biobío", "BIO", 8, true, LocalDate.of(2025, 1, 1)
        ));
        regionRepository.save(new Region(
                null, "Los Lagos", "LL", 10, false, LocalDate.of(2025, 1, 1)
        ));

        List<Region> regiones = regionRepository.findAll();

        assertEquals(2, regiones.size());
    }
}
