package com.arriendo.ms_reportes.config;

import com.arriendo.ms_reportes.model.Reporte;
import com.arriendo.ms_reportes.repository.ReporteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final ReporteRepository reporteRepository;

    public DataLoader(ReporteRepository reporteRepository) {
        this.reporteRepository = reporteRepository;
    }

    @Override
    public void run(String... args) {

        if (reporteRepository.count() == 0) {

            reporteRepository.save(new Reporte(
                    null,
                    "Reporte Mensual",
                    "Reporte de ingresos del mes de enero",
                    2500000.0,
                    45,
                    LocalDate.of(2026, 1, 31)
            ));

            reporteRepository.save(new Reporte(
                    null,
                    "Reporte Semanal",
                    "Reporte de reservas de la primera semana de febrero",
                    600000.0,
                    12,
                    LocalDate.of(2026, 2, 7)
            ));

            reporteRepository.save(new Reporte(
                    null,
                    "Reporte Diario",
                    "Reporte de ingresos del día 15 de marzo",
                    150000.0,
                    3,
                    LocalDate.of(2026, 3, 15)
            ));

            reporteRepository.save(new Reporte(
                    null,
                    "Reporte Trimestral",
                    "Reporte consolidado del primer trimestre",
                    7800000.0,
                    120,
                    LocalDate.of(2026, 3, 31)
            ));

            reporteRepository.save(new Reporte(
                    null,
                    "Reporte Mensual",
                    "Reporte de ingresos del mes de junio",
                    3200000.0,
                    58,
                    LocalDate.of(2026, 6, 30)
            ));

            reporteRepository.save(new Reporte(
                    null,
                    "Reporte Anual",
                    "Reporte consolidado del año 2025",
                    28000000.0,
                    410,
                    LocalDate.of(2025, 12, 31)
            ));
        }
    }
}
