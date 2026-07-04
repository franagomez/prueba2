package com.arriendo.ms_reportes.repository;

import com.arriendo.ms_reportes.model.Reporte;
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
class ReporteRepositoryTest {

    @Autowired
    private ReporteRepository reporteRepository;

    @Test
    void guardarYRecuperar_DebePersistirReporte() {
        Reporte reporte = new Reporte(
                null,
                "Reporte Mensual",
                "Reporte de ingresos de prueba",
                1200000.0,
                20,
                LocalDate.of(2026, 5, 10)
        );

        Reporte guardado = reporteRepository.save(reporte);

        assertNotNull(guardado.getId());

        Optional<Reporte> encontrado = reporteRepository.findById(guardado.getId());

        assertTrue(encontrado.isPresent());
        assertEquals("Reporte Mensual", encontrado.get().getTipoReporte());
        assertEquals(1200000.0, encontrado.get().getTotalIngresos());
        assertEquals(20, encontrado.get().getTotalReservas());
    }

    @Test
    void findAll_CuandoHayVariosReportes_DebeRetornarTodos() {
        reporteRepository.save(new Reporte(
                null, "Reporte Semanal", "Semana 1", 300000.0, 5, LocalDate.of(2026, 1, 7)
        ));
        reporteRepository.save(new Reporte(
                null, "Reporte Semanal", "Semana 2", 450000.0, 8, LocalDate.of(2026, 1, 14)
        ));

        List<Reporte> reportes = reporteRepository.findAll();

        assertEquals(2, reportes.size());
    }

    @Test
    void eliminar_DebeRemoverReporteDeLaBaseDeDatos() {
        Reporte guardado = reporteRepository.save(new Reporte(
                null, "Reporte Diario", "Día de prueba", 50000.0, 1, LocalDate.of(2026, 3, 1)
        ));

        Long id = guardado.getId();
        reporteRepository.deleteById(id);

        assertFalse(reporteRepository.findById(id).isPresent());
    }
}
